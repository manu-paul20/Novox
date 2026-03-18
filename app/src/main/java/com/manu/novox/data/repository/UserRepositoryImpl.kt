package com.manu.novox.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.data.local.entity.User
import com.manu.novox.domain.repository.UserRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
): UserRepository {
    override suspend fun addNewUserToChatList(userName: String) {

    }

    override suspend fun deleteUserFromChatList(userName: String) {

    }

}