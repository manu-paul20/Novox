package com.manu.novox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val userName: String = "",
    val name: String = "",
    val email: String = "",
    val profilePhoto: String = ""
)