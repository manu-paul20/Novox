package com.manu.novox.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.data.local.entity.Message
import com.manu.novox.domain.repository.ChatRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
) : ChatRepository {
    override fun getAllMessages(userName: String): Flow<List<Message>> {

    }
    override suspend fun deleteMessageFromChat(messageId: String) {

    }

    override suspend fun addMessageToChat(
        receiverUserName: String,
        text: String?,
        imageUrl: String?
    ) {

    }

    override suspend fun clearChat(receiverUserName: String) {

    }

}