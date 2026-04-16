package com.manu.novox.data.repository

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.core.utils.uploadToCloudinary
import com.manu.novox.data.local.dao.SettingsDao
import com.manu.novox.data.local.dao.UserDao
import com.manu.novox.data.local.entity.User
import com.manu.novox.data.local.entity.UserSettings
import com.manu.novox.domain.repository.AccountRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val firebaseDB: FirebaseDatabase,
    private val auth: FirebaseAuth,
    private val userDao: UserDao,
    private val credentialManager: CredentialManager,
    private val settingsDao: SettingsDao
) : AccountRepository{

    override suspend fun createAccount(name: String, userName: String) {
            val userRef = firebaseDB.getReference(MyConstants.DATABASE.USERS)

            if(searchUserByUserName(userName)){
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
                    settingsDao.updateKey(userName)
            }
    }

    override suspend fun updateAccountDetails(
        name: String,
        profilePicture: String
    ) {
        val currentUser = userDao.getUserDetails()
            val userRef = firebaseDB.getReference(MyConstants.DATABASE.USERS)
        val newUser = currentUser!!.copy(
                name = name,
                profilePhoto = profilePicture
            )
        userRef.child(currentUser.userName).setValue(newUser).await()
            userDao.updateUserDetails(newUser)
    }

    override suspend fun deleteAccount() {
        deleteProfilePhoto()
        val currentUser = userDao.getUserDetails()
        val userRef = firebaseDB.getReference(MyConstants.DATABASE.USERS)
        userRef.child(currentUser!!.userName).removeValue().await()
        userDao.deleteUser(currentUser)
    }

    override suspend fun changeProfilePhoto(
        newProfilePhoto: String,
    ) {
        deleteProfilePhoto()
        val newProfilePhoto = uploadToCloudinary(
            photoUrl = newProfilePhoto,
            onProgress = null
        )
        val currentUser = userDao.getUserDetails()
        userDao.updateUserDetails(
            currentUser!!.copy(
            profilePhoto = newProfilePhoto
        ))
    }

    suspend fun deleteProfilePhoto(): Unit = withContext(Dispatchers.IO) {
        val currentPhotoUrl = userDao.getUserDetails()!!.profilePhoto
        val publicId = currentPhotoUrl
            .substringAfterLast("/")
            .substringBeforeLast(".")
        val cloudinary = Cloudinary(MyConstants.CLOUDINARY.CLOUDINARY_CONFIG_MAP)
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap())
    }

    override suspend fun getAccountDetails(): User? {
        val currentUser = userDao.getUserDetails()
        return currentUser
    }



    override suspend fun signOut() {
        auth.signOut()
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }

    override suspend fun getUserFromEmail(): User? {
        val email = auth.currentUser?.email?:""
        val userRef = firebaseDB.getReference(MyConstants.DATABASE.USERS)
        val snapshot = userRef
            .orderByChild("email")
            .equalTo(email)
            .get()
            .await()
            if(snapshot.exists()){
                val userSnapShot = snapshot.children.first()
                val user = userSnapShot.getValue(User::class.java)
                return user
            }else{
                return null
            }

    }

    override suspend fun searchUserByUserName(userName: String): Boolean {
        val userRef = firebaseDB.getReference(MyConstants.DATABASE.USERS)
        val snapshot = userRef.child(userName).get().await()
        return snapshot.exists()
    }

    override suspend fun saveUserToDB(user: User) {
        userDao.addUser(user)
    }


}