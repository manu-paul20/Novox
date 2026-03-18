package com.manu.novox.domain.repository

import com.manu.novox.data.local.entity.User

interface UserRepository {

    suspend fun addNewUserToChatList(
        userName: String
    )

    suspend fun deleteUserFromChatList(
        userName: String
    )

}