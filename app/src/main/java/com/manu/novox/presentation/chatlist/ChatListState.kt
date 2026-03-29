package com.manu.novox.presentation.chatlist

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import com.manu.novox.data.local.entity.InteractedUsers
import com.manu.novox.data.local.entity.User
import com.manu.novox.data.local.entity.UserSettings

data class ChatListState(
    val isLocalSearchEnabled: Boolean = false,
    val isBottomSheetOpen: Boolean = false,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val error: String = "",
    val interactedUser: List<InteractedUsers> = emptyList(),
    val newUserName: String = "",
    val searchResult: User? = null,
    val isSearchingNewUser: Boolean = false,
    val searchingError: String = "",
    val fontSize:Int = 14,
    val fontFamily: FontFamily = FontFamily.Default,
    val fontStyle: FontStyle = FontStyle.Normal
)