package com.manu.novox.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manu.novox.core.utils.NovoxColors
import com.manu.novox.core.utils.NovoxFontFamily
import com.manu.novox.core.utils.NovoxFontStyle

@Entity(tableName = "settings")
data class UserSettings(
    @PrimaryKey val id: Int = 1,
    val fontFamily: NovoxFontFamily = NovoxFontFamily.Default,
    val fontSize: Int = 14,
    val fontStyle: NovoxFontStyle = NovoxFontStyle.Normal,
    val myMessageBoxColor: NovoxColors = NovoxColors.Light_Blue,
    val myTextColor: NovoxColors = NovoxColors.White,
    val friendsMessageBoxColor: NovoxColors = NovoxColors.Magenta,
    val friendsTextColor: NovoxColors = NovoxColors.White
)
