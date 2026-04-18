package com.manu.novox.presentation.personalization

import com.manu.novox.core.utils.NovoxColors
import com.manu.novox.core.utils.NovoxFontFamily
import com.manu.novox.core.utils.NovoxFontStyle

data class PersonalizationState(
    val isFontFamilyDropDownOpen: Boolean = false,
    val isMyMessageBoxColorDropDownOpen: Boolean = false,
    val isFriendsMessageBoxColorDropDownOpen: Boolean = false,
    val isTextColorDropDownOpen: Boolean = false,
    val fontFamily: NovoxFontFamily = NovoxFontFamily.Default,
    val textFontSize: Int = 14,
    val appFontSize: Int = 14,
    val fontStyle: NovoxFontStyle = NovoxFontStyle.Normal,
    val messageBoxColor: NovoxColors = NovoxColors.Light_Blue,
    val textColor: NovoxColors = NovoxColors.Black,
    val friendMessageBoxColor: NovoxColors = NovoxColors.White
)
