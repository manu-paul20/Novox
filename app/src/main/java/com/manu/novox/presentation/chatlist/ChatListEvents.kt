package com.manu.novox.presentation.chatlist

import com.manu.novox.data.local.entity.User

sealed interface ChatListEvents{
    object OpenLocalSearchBox: ChatListEvents

    object CloseLocalSearchBox: ChatListEvents

    data class OnSearchQueryChange(val query: String): ChatListEvents

    data class OnNewUserNameChange(val query: String): ChatListEvents

    object OpenNewUserSheet: ChatListEvents

    object CloseNewUserSheet: ChatListEvents

    data class SearchNewUser(val userName: String): ChatListEvents

    data class AddUser(val user: User): ChatListEvents

}