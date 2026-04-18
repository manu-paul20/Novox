package com.manu.novox.presentation.settings

data class SettingsNav(
    val onClickAccount:()-> Unit,
    val onClickPersonalization:()-> Unit,
    val onClickAbout:()-> Unit
)
