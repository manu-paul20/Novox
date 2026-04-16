package com.manu.novox.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manu.novox.core.utils.NovoxColors
import com.manu.novox.core.utils.NovoxFontFamily
import com.manu.novox.core.utils.NovoxFontStyle

@Entity(tableName = "settings")
data class UserSettings(
    @PrimaryKey val userName: String = "key0",
    val fontFamily: NovoxFontFamily = NovoxFontFamily.Default,
    val textFontSize: Int = 14,
    val appFontSize: Int = 14,
    val fontStyle: NovoxFontStyle = NovoxFontStyle.Normal,
    val messageBoxColor: NovoxColors = NovoxColors.Light_Blue,
    val textColor: NovoxColors = NovoxColors.Black,
    val friendMessageBoxColor: NovoxColors = NovoxColors.White
)
