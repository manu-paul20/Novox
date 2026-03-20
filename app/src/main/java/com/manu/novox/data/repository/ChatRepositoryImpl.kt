package com.manu.novox.data.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.core.utils.getChatId
import com.manu.novox.data.local.dao.MessageDao
import com.manu.novox.data.local.dao.UserDao
import com.manu.novox.data.local.entity.Message
import com.manu.novox.domain.model.FirebaseUpdate
import com.manu.novox.domain.repository.ChatRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val messageDao: MessageDao,
    private val userDao: UserDao,
    private val externalScope: CoroutineScope
) : ChatRepository {

    private var firebaseJob: Job? = null

    override suspend fun getAllMessages(userName: String): Flow<List<Message>> {
        val currentUser = userDao.getUserDetails().first()
        val chatId = getChatId(currentUser.userName, userName)

        firebaseJob?.cancel()

        firebaseJob = externalScope.launch {
            syncMessageFromFirebase(chatId).collect { update ->
                when (update) {
                    is FirebaseUpdate.MessageAdded -> messageDao.insertMessage(update.message)
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
        text: String?,
        imageUrl: String?
    ) {

    }

    override suspend fun clearChat(receiverUserName: String) {

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
                    trySend(FirebaseUpdate.MessageRemoved(it.messageId))
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

}