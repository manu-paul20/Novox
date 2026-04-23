package com.manu.novox.presentation.settings.screens

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SettingsListItem(
    onClick:()-> Unit,
    headLineText: String,
    supportingText: String,
    icon: ImageVector
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),

        colors = ListItemDefaults.colors(
            containerColor = Color(0xFFF9F6F0)
        ),
        headlineContent = {Text(
            text = headLineText,
            fontWeight = FontWeight.Bold
        )},
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = "About app"
            )
        },
        supportingContent = {
            Text(supportingText)
        }
    )
}