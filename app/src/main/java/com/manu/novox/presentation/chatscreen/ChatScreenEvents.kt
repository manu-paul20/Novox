package com.manu.novox.presentation.chatscreen

sealed interface ChatScreenEvents {
    data class SetUserDetails(val userName: String,val profilePhoto: String): ChatScreenEvents

    object SendMessage: ChatScreenEvents

    data class SetMessage(val message: String): ChatScreenEvents

    object ClearChat: ChatScreenEvents

    object ToggleTyping: ChatScreenEvents
}