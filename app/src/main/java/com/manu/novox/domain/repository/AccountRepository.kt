package com.manu.novox.domain.repository

interface AccountRepository {
    suspend fun createAccount(
        name: String,
        userName: String
    )
    suspend fun updateAccountDetails(
        name: String,
        profilePicture: String
    )

    suspend fun changeProfilePhoto(
        newProfilePhoto: String,
    ): String

    suspend fun deleteAccount()
}