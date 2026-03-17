package com.manu.novox.domain.repository

import com.manu.novox.domain.model.User

interface UserRepository {
    suspend fun getUser(uid: String): User?

    suspend fun isUserExist(uid: String): Boolean

    suspend fun addUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)

}