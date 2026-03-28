package com.manu.novox.presentation.chatlist

import com.manu.novox.data.local.entity.InteractedUsers
import com.manu.novox.data.local.entity.User

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
    val searchingError: String = ""
)