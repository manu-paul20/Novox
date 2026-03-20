package com.manu.novox.domain.repository

import com.manu.novox.data.local.entity.User

interface UserRepository {

    suspend fun addNewUserToChatList(
       user: User
    )

    suspend fun deleteUserFromChatList(
        userName: String
    )

    suspend fun searchUser(userName: String): User

}