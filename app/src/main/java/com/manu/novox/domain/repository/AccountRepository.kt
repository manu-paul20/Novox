package com.manu.novox.domain.repository

interface AccountRepository {
    suspend fun createAccount(
        name: String,
        userName: String
    )
    suspend fun updateAccountDetails(
        name: String,
        userName: String,
        profilePicture: String
    )


    suspend fun deleteAccount()
}