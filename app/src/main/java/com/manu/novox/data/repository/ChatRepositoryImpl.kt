package com.manu.novox.data.repository

import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.core.utils.getChatId
import com.manu.novox.core.utils.uploadToCloudinary
import com.manu.novox.data.local.dao.InteractedUsersDao
import com.manu.novox.data.local.dao.MessageDao
import com.manu.novox.data.local.dao.UserDao
import com.manu.novox.data.local.entity.InteractedUsers
import com.manu.novox.data.local.entity.Message
import com.manu.novox.data.local.entity.User
import com.manu.novox.domain.model.FirebaseUpdate
import com.manu.novox.domain.model.InboxItem
import com.manu.novox.domain.repository.ChatRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val messageDao: MessageDao,
    private val userDao: UserDao,
    private val externalScope: CoroutineScope,
    private val interactedUsersDao: InteractedUsersDao
) : ChatRepository {

    private var firebaseJob: Job? = null

    override suspend fun getAllMessages(userName: String): Flow<List<Message>> {
        val currentUser = userDao.getUserDetails()
        val chatId = getChatId(currentUser!!.userName, userName)

        firebaseJob?.cancel()

        firebaseJob = externalScope.launch {
            syncMessageFromFirebase(chatId).collect { update ->
                when (update) {
                    is FirebaseUpdate.MessageAdded -> {

                        messageDao.insertMessage(update.message)
                            interactedUsersDao.updateLastInteractionDetails(
                                userName,
                                update.message.timeStamp,
                                update.message.text
                            )
                                messageDao.insertMessage(update.message)
                    }
                    is FirebaseUpdate.MessageRemoved -> messageDao.deleteMessage(update.messageId)
                }
            }
        }

        return messageDao.getMessages(chatId)
    }

    override suspend fun deleteMessageFromChat(chatId: String, messageId: String) {
        messageDao.deleteMessage(messageId)
        val chatRef = database.getReference(MyConstants.DATABASE.MESSAGES).child(chatId)
        chatRef.child(messageId).removeValue()
    }

    override suspend fun addMessageToChat(
        receiverUserName: String,
        text: String,
        imageUrl: String,
        onProgress: ((String) -> Unit)?
    ) {
        val currentUser = userDao.getUserDetails()
        val chatId = getChatId(currentUser!!.userName,receiverUserName)
        val chatRef = database.getReference(MyConstants.DATABASE.MESSAGES).child(chatId)
        val messageId = chatRef.push().key!!
        val inboxRef = database.getReference(MyConstants.DATABASE.INBOX)
        //first add the image to chat
        var message = Message(
            messageId = messageId,
            senderUserName = currentUser!!.userName,
            text = text,
            image = imageUrl,
            timeStamp = System.currentTimeMillis(),
            chatId = chatId
        )
        messageDao.insertMessage(message)

        val cloudinaryUrl = if (imageUrl.isNotBlank()) {
            uploadToCloudinary(
                photoUrl = imageUrl,
                onProgress = onProgress
            )
        }else{
            ""
        }

        message = message.copy(image = cloudinaryUrl)
        messageDao.insertMessage(message)
        chatRef.child(messageId).setValue(message)

        inboxRef.child(receiverUserName).child(currentUser.userName).setValue(
            InboxItem(
                message = message.text,
                timeStamp = message.timeStamp
            )
        )
        inboxRef.child(currentUser.userName).child(receiverUserName).setValue(
            InboxItem(
                message = message.text,
                timeStamp = message.timeStamp
            )
        )


        interactedUsersDao.updateLastInteractionDetails(
            receiverUserName,
            System.currentTimeMillis(),
            message.text
        )

    }

    override suspend fun clearChat(receiverUserName: String) {
        val currentUser = userDao.getUserDetails()
        val chatId = getChatId(currentUser!!.userName,receiverUserName)
        messageDao.deleteAllMessages(chatId) //only deletes local messages

    }

     fun syncMessageFromFirebase(chatId: String): Flow<FirebaseUpdate> = callbackFlow {
        val messageRef = database.getReference(MyConstants.DATABASE.MESSAGES).child(chatId)
        val listener = object : ChildEventListener {
            override fun onChildAdded(
                p0: DataSnapshot,
                p1: String?
            ) {
                p0.getValue(Message::class.java)?.let {
                    trySend(FirebaseUpdate.MessageAdded(it))
                }
            }

            override fun onChildChanged(
                p0: DataSnapshot,
                p1: String?
            ) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                p0.getValue(Message::class.java)?.let {
                    trySend(FirebaseUpdate.MessageRemoved(
                        it.messageId
                    ))
                }

            }

            override fun onChildMoved(
                p0: DataSnapshot,
                p1: String?
            ) {
            }

            override fun onCancelled(p0: DatabaseError) {
                close(p0.toException())
            }
        }

        messageRef.addChildEventListener(listener)

        awaitClose {
            messageRef.removeEventListener(listener)
        }
    }

    override  fun syncAllContactsFromFirebase(coroutineScope: CoroutineScope) {
        coroutineScope.launch(Dispatchers.IO) {
            val userName = userDao.getUserDetails()?.userName ?: ""
            val inboxRef = database.getReference(MyConstants.DATABASE.INBOX)
            val inboxItems = inboxRef.child(userName).get().await().children
            inboxItems.forEach { inboxItem ->
                val item = inboxItem.getValue(InboxItem::class.java)
                val userName = inboxItem.key!!
                if (!interactedUsersDao.isUserExist(userName)) {
                    val snapshot = database
                        .getReference(MyConstants.DATABASE.USERS)
                        .child(userName)
                        .get()
                        .await()
                    val user = snapshot.getValue(User::class.java)
                    user?.let {
                        interactedUsersDao.addUser(
                            InteractedUsers(
                                name = it.name,
                                userName = it.userName,
                                lastMessage = item?.message ?: "",
                                lastInteracted = item?.timeStamp ?: 0L,
                                profilePhoto = it.profilePhoto
                            )
                        )
                    }
                } else {
                    interactedUsersDao.updateLastInteractionDetails(
                        userName = userName,
                        lastMessage = item?.message ?: "",
                        lastInteractionTime = item?.timeStamp ?: 0L
                    )
                }
            }
        }
    }

    override fun startListeningToInbox(coroutineScope: CoroutineScope) {
        coroutineScope.launch(Dispatchers.IO) {
            val myUserName = userDao.getUserDetails()?.userName ?: return@launch
            val inboxRef = database.getReference(MyConstants.DATABASE.INBOX).child(myUserName)

            inboxRef.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(
                    p0: DataSnapshot,
                    p1: String?
                ) {
                    val userName = p0.key ?: return
                    val inboxItem = p0.getValue(InboxItem::class.java)
                    coroutineScope.launch(Dispatchers.IO) {
                        if (!interactedUsersDao.isUserExist(userName)) {
                            val userSnapshot = database.getReference(MyConstants.DATABASE.USERS)
                                .child(userName)
                                .get()
                                .await()
                            val user = userSnapshot.getValue(User::class.java)

                            user?.let {
                                interactedUsersDao.addUser(
                                    user = InteractedUsers(
                                        userName = it.userName,
                                        profilePhoto = it.profilePhoto,
                                        name = it.name,
                                        lastMessage = inboxItem?.message ?: "",
                                        lastInteracted = inboxItem?.timeStamp
                                            ?: System.currentTimeMillis()
                                    )
                                )
                                interactedUsersDao.updateLastInteractionDetails(
                                    userName = it.userName,
                                    lastMessage = inboxItem?.message ?: "",
                                    lastInteractionTime = inboxItem?.timeStamp
                                        ?: System.currentTimeMillis()
                                )
                            }
                        }
                    }
                }

                override fun onChildChanged(
                    p0: DataSnapshot,
                    p1: String?
                ) {
                    val userName = p0.key ?: return
                    val inboxItem = p0.getValue(InboxItem::class.java)
                    coroutineScope.launch(Dispatchers.IO) {
                        interactedUsersDao.updateLastInteractionDetails(
                           userName = userName,
                            lastMessage = inboxItem?.message?:"",
                            lastInteractionTime = inboxItem?.timeStamp?: System.currentTimeMillis()
                        )
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

                override fun onChildMoved(
                    p0: DataSnapshot,
                    p1: String?
                ) {

                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
        }
    }

}