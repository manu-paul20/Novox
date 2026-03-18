package com.manu.novox.domain.repository

import com.manu.novox.data.local.entity.Message
import kotlinx.coroutines.flow.Flow


interface ChatRepository {
    suspend fun deleteMessageFromChat(
        messageId: String
    )

    fun getAllMessages(userName: String): Flow<List<Message>>


    suspend fun addMessageToChat(
        receiverUserName: String,
        text: String?,
        imageUrl: String?
    )

    suspend fun clearChat(
        receiverUserName: String
    )
}