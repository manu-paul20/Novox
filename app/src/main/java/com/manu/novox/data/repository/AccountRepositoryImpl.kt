package com.manu.novox.data.repository

import com.manu.novox.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(

) : AccountRepository{
    override suspend fun createAccount(name: String, userName: String) {

    }

    override suspend fun updateAccountDetails(
        name: String,
        userName: String,
        profilePicture: String
    ) {

    }

    override suspend fun deleteAccount() {

    }

}