package com.manu.novox.domain.repository

import android.content.Context
import com.manu.novox.domain.model.SignInResult

interface AuthRepository {
    suspend fun signInWithGoogle(context: Context)
    suspend fun signInWithEmail(email: String, password: String)
    suspend fun signUpWithEmail(email: String, password: String)

    suspend fun resetPassword(email: String)

    fun isUserLoggedIn(): Boolean

}