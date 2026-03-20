package com.manu.novox.data.repository

import androidx.core.net.toUri
import com.cloudinary.Cloudinary
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.utils.ObjectUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.manu.novox.data.local.dao.UserDao
import com.manu.novox.data.local.entity.User
import com.manu.novox.domain.repository.AccountRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

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
        userRef.child(currentUser.userName).setValue(newUser).await()
            userDao.updateUserDetails(newUser)
    }

    override suspend fun deleteAccount() {
        deleteProfilePhoto()
        val currentUser = userDao.getUserDetails().first()
        val userRef = firebaseDB.getReference(MyConstants.DATABASE.USERS)
        userRef.child(currentUser.userName).removeValue()
        userDao.deleteUser(currentUser)
    }

    override suspend fun changeProfilePhoto(
        newProfilePhoto: String
    ): String {
        deleteProfilePhoto()
        return suspendCancellableCoroutine { continuation ->
            val request = MediaManager.get()
                .upload(newProfilePhoto.toUri())
                .callback(object : UploadCallback {
                    override fun onStart(p0: String?) {

                    }

                    override fun onProgress(p0: String?, p1: Long, p2: Long) {

                    }

                    override fun onSuccess(p0: String?, p1: Map<*, *>?) {
                        val profilePicture = p1?.get("secure_url").toString()
                        if (continuation.isActive) {
                            continuation.resume(profilePicture)
                        }
                    }

                    override fun onError(
                        p0: String?,
                        p1: ErrorInfo?
                    ) {
                        if (continuation.isActive) {
                            continuation.resumeWithException(
                                Exception(p1?.description ?: "Something went wrong")
                            )
                        }
                    }

                    override fun onReschedule(
                        p0: String?,
                        p1: ErrorInfo?
                    ) {

                    }
                }).dispatch()
            continuation.invokeOnCancellation {
                MediaManager.get().cancelRequest(request)
            }
        }
    }

    suspend fun deleteProfilePhoto(): Unit = withContext(Dispatchers.IO) {
        val currentPhotoUrl = userDao.getUserDetails().first().profilePhoto
        val publicId = currentPhotoUrl
            .substringAfterLast("/")
            .substringBeforeLast(".")
        val cloudinary = Cloudinary(MyConstants.CLOUDINARY.CLOUDINARY_CONFIG_MAP)
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap())
    }

    override suspend fun getAccountDetails(): User {
        val currentUser = userDao.getUserDetails().first()
        return currentUser
    }

}