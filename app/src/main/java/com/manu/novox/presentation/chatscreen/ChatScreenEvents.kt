package com.manu.novox.presentation.chatscreen

import android.content.Context

sealed interface ChatScreenEvents {
    data class SetUserDetails(val userName: String,val profilePhoto: String): ChatScreenEvents

    object SendMessage: ChatScreenEvents

    data class SetMessage(val message: String): ChatScreenEvents
    data class SetImageUrl(val url: String): ChatScreenEvents

    object ClearImage: ChatScreenEvents

    object ClearChat: ChatScreenEvents

    object ToggleDropDown: ChatScreenEvents

    data class ShowImageInFullScreen(val imageUrl: String): ChatScreenEvents

    object CloseFullScreenImage: ChatScreenEvents

    data class DownloadImage(val context: Context): ChatScreenEvents
}