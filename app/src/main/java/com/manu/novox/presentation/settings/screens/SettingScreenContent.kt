package com.manu.novox.presentation.settings.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingScreenContent(
    modifier: Modifier,
    onClickPersonalization:()-> Unit
) {
    Column(
        modifier = modifier
            .background(Color(0xFFF9F6F0))
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
       SettingsListItem(
           onClick = {},
           headLineText = "Account",
           supportingText = "Manage your account",
           icon = Icons.Default.AccountCircle
       )
        HorizontalDivider()
        SettingsListItem(
            onClick = {onClickPersonalization()},
            headLineText = "Personalization",
            supportingText = "Customize your app",
            icon = Icons.Default.Brush

        )

        HorizontalDivider()

        SettingsListItem(
            onClick = {nav.onClickAbout()},
            headLineText = "About",
            supportingText = "About the app",
            icon = Icons.Default.Info
        )

    }
}