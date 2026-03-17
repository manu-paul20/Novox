package com.manu.novox.domain.repository

import android.content.Context
import com.manu.novox.domain.model.SignInResult

interface AuthRepository {
    suspend fun signInWithGoogle(context: Context): SignInResult
    suspend fun signInWithEmail(email: String, password: String): SignInResult
    suspend fun signUpWithEmail(email: String, password: String): SignInResult
    suspend fun signOut()
}