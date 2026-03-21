package com.manu.novox.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.core.utils.getChatId
import com.manu.novox.data.local.dao.InteractedUsersDao
import com.manu.novox.data.local.dao.UserDao
import com.manu.novox.data.local.entity.InteractedUsers
import com.manu.novox.data.local.entity.User
import com.manu.novox.domain.repository.InteractedUserRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InteractedUserRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
    private val interactedUsersDao: InteractedUsersDao,
    private val userDao: UserDao
): InteractedUserRepository {
    override suspend fun addNewUserToChatList(user: User) {
        //here we need to do only one thing
        //1. Add the new user to interacted user list
        try {
                interactedUsersDao.addUser(
                    user = InteractedUsers(
                        name = user.name,
                        userName = user.userName,
                        profilePhoto = user.profilePhoto,
                        lastInteracted = System.currentTimeMillis()
                    )
                )

        } catch (e: Exception) {
            throw  e
        }
    }

    override suspend fun deleteUserFromChatList(userName: String) {
        /*
        Here we need to do two things
        1. Delete the user from interacted user list
        2. Delete the user from firebase
         */
        try {
           val currentUser = userDao.getUserDetails().first()
            val chatId = getChatId(currentUser.userName,userName)
            val chatRef = database.getReference(MyConstants.DATABASE.MESSAGES)
            chatRef.child(chatId).removeValue().await() // deleted from firebase
            interactedUsersDao.deleteUser(userName) //deleted from local db
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun searchUser(userName: String): User {
        try {
            val userRef = database.getReference(MyConstants.DATABASE.USERS)
            val userDataSnapshot = userRef.child(userName).get().await()
            if (userDataSnapshot.exists()){
                val user = userDataSnapshot.getValue(User::class.java)
                return user!!
            }else{
                throw Exception("user don't exist")
            }
        } catch (e: Exception) {
            throw  e
        }
    }

    override fun getAllUser(): Flow<List<InteractedUsers>> {
        return interactedUsersDao.getAllUsers()
    }

}