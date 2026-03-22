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
    object ChatScreen : Routes

    @Serializable
    object SettingsScreen: Routes
}