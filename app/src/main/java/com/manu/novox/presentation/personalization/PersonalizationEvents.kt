package com.manu.novox.presentation.personalization

import com.manu.novox.core.utils.NovoxColors
import com.manu.novox.core.utils.NovoxFontFamily
import com.manu.novox.core.utils.NovoxFontStyle

sealed interface PersonalizationEvents {
    data class OnFontFamilyDropDownChange(val value: Boolean) : PersonalizationEvents
    data class OnMessageBoxColorDropDown(val value: Boolean) : PersonalizationEvents

    data class OnFriendMessageBoxColorDropDown(val value: Boolean) : PersonalizationEvents
    data class OnTextColorDropDown(val value: Boolean) : PersonalizationEvents

    object Save : PersonalizationEvents
    object Close : PersonalizationEvents

    data class OnFontFamilyChange(val fontFamily: NovoxFontFamily) : PersonalizationEvents
    data class OnTextFontSizeChange(val fontSize: Int) : PersonalizationEvents
    data class OnAppFontSizeChange(val fontSize: Int) : PersonalizationEvents
    data class OnFontStyleChange(val fontStyle: NovoxFontStyle) : PersonalizationEvents
    data class OnMessageBoxColorChange(val color: NovoxColors) : PersonalizationEvents
    data class OnTextColorChange(val color: NovoxColors) : PersonalizationEvents
    data class OnFriendMessageBoxColorChange(val color: NovoxColors) : PersonalizationEvents
}