package com.manu.novox.data.local.entity

import androidx.room.Entity

@Entity(tableName = "interacted_users")
data class InteractedUsers(
    val id: String,
    val name: String,
    val userName: String,
    val profilePhoto: String,
)
