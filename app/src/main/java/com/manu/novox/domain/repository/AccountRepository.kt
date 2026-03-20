package com.manu.novox.domain.repository

import com.manu.novox.data.local.entity.User

interface AccountRepository {
    suspend fun createAccount(
        name: String,
        userName: String
    )
    suspend fun updateAccountDetails(
        name: String,
        profilePicture: String
    )

   suspend fun getAccountDetails(): User

    suspend fun changeProfilePhoto(
        newProfilePhoto: String,
    ): String

    suspend fun deleteAccount()
}