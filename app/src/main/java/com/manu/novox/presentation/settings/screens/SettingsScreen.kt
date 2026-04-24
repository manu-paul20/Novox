package com.manu.novox.presentation.settings.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manu.novox.presentation.settings.SettingsEvent
import com.manu.novox.presentation.settings.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onCLickPersonalization: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    LaunchedEffect(
        Unit
    ) {
        viewModel.getAccountDetails()
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onEvent = viewModel::onEvent

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF9F6F0)
                ),
                title = { Text("Settings") }
            )
        }
    ) {innerpadding->
        SettingScreenContent(
            modifier = Modifier.padding(innerpadding),
            onClickPersonalization = onCLickPersonalization
        )
    }

    if (state.isAboutAppPopUpOpen) {
        AboutTheAppPopUp(
            state = state,
            onDismiss = { onEvent(SettingsEvent.CloseAboutAppPopUp) }
        )
    }

    if (state.isAboutAccountPopUpOpen) {
        AboutAccountPopUp(
            state = state,
            onDismiss = { onEvent(SettingsEvent.CloseAboutAccountPopUp) }
        )
    }


}