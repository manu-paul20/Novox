package com.manu.novox.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.domain.model.User
import com.manu.novox.domain.repository.UserRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase
): UserRepository {
    override suspend fun  getUser(uid: String): User? {
        val userDB = database.getReference(MyConstants.DATABASE.USERS)
        val user = userDB.child(uid).get().await()
        return user.getValue(User::class.java)
    }

    override suspend fun isUserExist(uid: String): Boolean {
        val userDB = database.getReference(MyConstants.DATABASE.USERS)
        val user = userDB.child(uid).get().await()
        return user.exists()
    }

    override suspend fun addUser(user: User) {
        val userDB = database.getReference(MyConstants.DATABASE.USERS)
        val user = userDB.child(user.id)
        user.setValue(user)
    }

    override suspend fun updateUser(user: User) {
        val userDB = database.getReference(MyConstants.DATABASE.USERS)
        val user = userDB.child(user.id)
        user.setValue(user)
    }

    override suspend fun deleteUser(user: User) {
        val userDB = database.getReference(MyConstants.DATABASE.USERS)
        val user = userDB.child(user.id)
        user.removeValue()
    }


}