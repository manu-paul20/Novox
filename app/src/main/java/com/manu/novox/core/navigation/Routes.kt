package com.manu.novox.core.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    object AuthScreen : Routes

    @Serializable
    object AccountCreationScreen : Routes

    @Serializable
    object ChatListScreen : Routes

    @Serializable
    data class ChatScreen(val userName: String,val profilePhoto: String,val name: String) : Routes

    @Serializable
    object SettingsScreen: Routes

    @Serializable
    object Personalization: Routes
}