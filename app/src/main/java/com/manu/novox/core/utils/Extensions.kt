package com.manu.novox.core.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle

fun NovoxFontFamily.toFontFamily(): FontFamily {
    return when (this) {
        NovoxFontFamily.Serif -> FontFamily.Serif
        NovoxFontFamily.Monospace -> FontFamily.Monospace
        NovoxFontFamily.Sans_Serif -> FontFamily.SansSerif
        NovoxFontFamily.Cursive -> FontFamily.Cursive
        NovoxFontFamily.Default -> FontFamily.Default
    }
}

fun NovoxFontStyle.toFontStyle(): FontStyle {
    return when (this) {
        NovoxFontStyle.Italic -> FontStyle.Italic
        NovoxFontStyle.Normal -> FontStyle.Normal
    }
}

fun NovoxColors.toColor(): Color {
    return when (this) {
        NovoxColors.Red -> Color.Red
        NovoxColors.Green -> Color.Green
        NovoxColors.Blue -> Color.Blue
        NovoxColors.Magenta -> Color.Magenta
        NovoxColors.Cyan -> Color.Cyan
        NovoxColors.Yellow -> Color.Yellow
        NovoxColors.Black -> Color.Black
        NovoxColors.White -> Color.White
        NovoxColors.Gray -> Color.Gray
        NovoxColors.Light_Gray -> Color.LightGray
        NovoxColors.Dark_Gray -> Color.DarkGray
        NovoxColors.Light_Blue -> Color(0xFF03A9F4)
    }
}