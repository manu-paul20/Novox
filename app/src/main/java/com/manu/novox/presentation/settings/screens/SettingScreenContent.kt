package com.manu.novox.presentation.settings.screens

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
fun SettingScreenContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.White
            ),
            headlineContent = { Text(
               text =  "Account",
                fontWeight = FontWeight.Bold
            ) },
            supportingContent = { Text("Know about your account") },
            leadingContent = { Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Account settings"
            ) }
        )
        HorizontalDivider()

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.White
            ),
            headlineContent = {
                Text(
                    text = "Personalization",
                    fontWeight = FontWeight.Bold
                )
            },
            leadingContent = {Icon(
                imageVector = Icons.Default.Brush,
                contentDescription = "personalization"
            )},
            supportingContent = {
                Text("Customize the app")
            }
        )

        HorizontalDivider()

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = Color.White
            ),
            headlineContent = {Text(
                text = "About App",
                fontWeight = FontWeight.Bold
            )},
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "About app"
                )
            },
            supportingContent = {
                Text("Know about the app")
            }
        )
    }
}