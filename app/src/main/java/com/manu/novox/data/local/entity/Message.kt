package com.manu.novox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    val senderId: String,
    @PrimaryKey val receiverId: String,
    val text: String,
    val image: String?,
    val timeStamp: Long
)