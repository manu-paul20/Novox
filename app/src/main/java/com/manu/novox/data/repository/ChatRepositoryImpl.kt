package com.manu.novox.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.data.local.entity.Message
import com.manu.novox.domain.repository.ChatRepository
import com.manu.novox.others.MyConstants
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : ChatRepository {
    override suspend fun addMessage(message: Message) {
        val chatId = "${message.senderId}_${message.receiverId}"
        val messageDB = database.getReference(MyConstants.DATABASE.MESSAGES)
        val chatRef = messageDB.child(chatId)
        val newMessageRef = chatRef.push()
        newMessageRef.setValue(message)
    }

    override suspend fun deleteMessage(message: Message) {
        val chatId = "${message.senderId}_${message.receiverId}"
        val messageDB = database.getReference(MyConstants.DATABASE.MESSAGES)
        val chatRef = messageDB.child(chatId)
        val messageRef = chatRef.child(chatId)
        messageRef.removeValue()
    }
}