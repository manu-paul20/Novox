package com.manu.novox.presentation.chatscreen

import com.manu.novox.presentation.chatlist.ChatListEffects

sealed interface ChatScreenEffects{
    data class ShowToast(val message: String): ChatScreenEffects
}