package com.manu.novox.domain.repository

import com.manu.novox.data.local.entity.Message

interface ChatRepository {
    suspend fun addMessage(message: Message)

    suspend fun deleteMessage(message: Message)
}