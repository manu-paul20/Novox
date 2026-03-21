package com.manu.novox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interacted_users")
data class InteractedUsers(
    @PrimaryKey val userName: String,
    val name: String,
    val profilePhoto: String,
    val lastInteracted: Long
)
