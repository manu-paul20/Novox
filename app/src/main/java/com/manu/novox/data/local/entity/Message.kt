package com.manu.novox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(

    @PrimaryKey val messageId: String="",
    val chatId: String="",
    val senderUserName: String="",
    val text: String="",
    val image: String="",
    val timeStamp: Long=0L
)