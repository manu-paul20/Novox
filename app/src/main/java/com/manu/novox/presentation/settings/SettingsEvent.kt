package com.manu.novox.presentation.settings

sealed interface SettingsEvent {
    object OpenAboutAppPopUp : SettingsEvent
    object CloseAboutAppPopUp : SettingsEvent

    object OpenAboutAccountPopUp : SettingsEvent
    object CloseAboutAccountPopUp : SettingsEvent
}
