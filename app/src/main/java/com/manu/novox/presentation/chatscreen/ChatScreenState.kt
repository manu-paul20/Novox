package com.manu.novox.presentation.chatscreen

import com.manu.novox.data.local.entity.Message

data class ChatScreenState(
    val userName: String = "",
    val profilePhoto: String = "",
   val message: String = "",
    val imageUrl: String = "",
    val messagesList: List<Message> = emptyList(),
    val isTyping: Boolean = false
)