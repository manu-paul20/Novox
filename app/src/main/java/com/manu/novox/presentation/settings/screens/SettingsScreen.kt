package com.manu.novox.presentation.settings.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manu.novox.presentation.settings.SettingsNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    nav: SettingsNav
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) {innerpadding->
        SettingScreenContent(
            modifier = Modifier.padding(innerpadding),
            nav = nav
        )
    }
}