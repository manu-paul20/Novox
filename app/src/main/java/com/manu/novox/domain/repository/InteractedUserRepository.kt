package com.manu.novox.domain.repository

import com.manu.novox.data.local.entity.InteractedUsers
import com.manu.novox.data.local.entity.User
import kotlinx.coroutines.flow.Flow

interface InteractedUserRepository {

    suspend fun addNewUserToChatList(
       user: User
    )

    suspend fun deleteUserFromChatList(
        userName: String
    )

     fun getAllUser(): Flow<List<InteractedUsers>>

    suspend fun searchUser(userName: String): User

    suspend fun updateInteractedUserDetails(userName: String,lastInteracted: Long): InteractedUsers?

}