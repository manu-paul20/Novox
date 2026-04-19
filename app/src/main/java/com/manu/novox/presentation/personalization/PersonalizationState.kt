package com.manu.novox.presentation.personalization

import com.manu.novox.core.utils.NovoxColors
import com.manu.novox.core.utils.NovoxFontFamily
import com.manu.novox.core.utils.NovoxFontStyle
import com.manu.novox.data.local.entity.UserSettings

data class PersonalizationState(
    val isFontFamilyDropDownOpen: Boolean = false,
    val isMyMessageBoxColorDropDownOpen: Boolean = false,
    val isFriendsMessageBoxColorDropDownOpen: Boolean = false,
    val isTextColorDropDownOpen: Boolean = false,
    val settings: UserSettings = UserSettings()
)
