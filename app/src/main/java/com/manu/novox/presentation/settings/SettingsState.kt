package com.manu.novox.presentation.settings

import com.manu.novox.core.utils.NovoxFontFamily
import com.manu.novox.core.utils.NovoxFontStyle

data class SettingsState(
    val fontFamily: NovoxFontFamily = NovoxFontFamily.Default,
    val appFontSize: Int = 14,
    val fontStyle: NovoxFontStyle = NovoxFontStyle.Normal,
    val isAboutAppPopUpOpen: Boolean = false,
    val userName: String="",
    val name: String="",
    val profilePic: String="",
    val email: String="",
    val isAboutAccountPopUpOpen: Boolean = false
)
