package com.manu.novox.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val title: String,
    val icon: ImageVector
)

val TOP_LEVEL_DESTINATION = mapOf(
    Routes.ChatListScreen to NavItem(
        title = "Chats",
        icon = Icons.Default.ChatBubble
    ),
    Routes.SettingsScreen to NavItem(
        title = "Settings",
        icon = Icons.Default.Settings
    )
)