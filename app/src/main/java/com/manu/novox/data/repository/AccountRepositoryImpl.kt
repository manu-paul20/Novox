package com.manu.novox.data.repository

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.data.local.NovoxDatabase
import com.manu.novox.data.local.dao.UserDao
import com.manu.novox.data.local.entity.User
import com.manu.novox.domain.repository.AccountRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val firebaseDB: FirebaseDatabase,
    private val auth: FirebaseAuth,
    private val userDao: UserDao
) : AccountRepository{



    override suspend fun createAccount(name: String, userName: String) {
        try {
            val userRef = firebaseDB.getReference(MyConstants.DATABASE.USERS)
            val isUserExist = userRef.child(userName).get().await().exists()
            if(isUserExist){
                throw Exception("User already exists")
            }else{
                val user = User(
                    name = name,
                    userName = userName,
                    email = auth.currentUser?.email?:"",
                    profilePhoto = auth.currentUser?.photoUrl.toString()
                )
                    userRef.child(userName).setValue(user).await()
                    userDao.addUser(user)

            }
        } catch (e: Exception) {
            throw Exception("Account creation failed:${e.localizedMessage}")
        }
    }

    override suspend fun updateAccountDetails(
        name: String,
        profilePicture: String
    ) {
            val currentUser = userDao.getUserDetails().first()
            val userRef = firebaseDB.getReference(MyConstants.DATABASE.USERS)
            val newUser = currentUser.copy(
                name = name,
                profilePhoto = profilePicture
            )
            userRef.child(currentUser.userName).setValue(newUser)
            userDao.updateUserDetails(newUser)
    }

    override suspend fun deleteAccount() {
        val currentUser = userDao.getUserDetails().first()
        val userRef = firebaseDB.getReference(MyConstants.DATABASE.USERS)
        userRef.child(currentUser.userName).removeValue()
        userDao.deleteUser(currentUser)
    }

}