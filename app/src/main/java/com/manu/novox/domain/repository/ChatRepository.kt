package com.manu.novox.domain.repository

import com.manu.novox.data.local.entity.Message
import kotlinx.coroutines.flow.Flow


interface ChatRepository {
    suspend fun deleteMessageFromChat(
        chatId: String,
        messageId: String
    )

    suspend fun getAllMessages(userName: String): Flow<List<Message>>


    suspend fun addMessageToChat(
        receiverUserName: String,
        text: String,
        imageUrl: String,
        onProgress:((String)-> Unit)?
    )

    suspend fun clearChat(
        receiverUserName: String
    )
}