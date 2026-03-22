package com.manu.novox.data.repository

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.manu.novox.domain.repository.AuthRepository
import com.manu.novox.others.MyConstants
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val credentialManager: CredentialManager,
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun signInWithGoogle(context: Context) {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(MyConstants.FIREBASE.WEB_CLIENT_ID)
                .setFilterByAuthorizedAccounts(false)
                .build()
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(context, request)

            if (
                result.credential is CustomCredential &&
                result.credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ) {
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(result.credential.data)
                signInWithFirebase(googleIdTokenCredential.idToken)
            } else {
                throw Exception("Invalid credential type")
            }
    }

    override suspend fun signInWithEmail(email: String, password: String) {
            auth.signInWithEmailAndPassword(email,password).await()

    }

    override suspend fun signUpWithEmail(email: String, password: String) {
            auth.createUserWithEmailAndPassword(email,password).await()
    }


    suspend fun signInWithFirebase(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).await()

    }

    override suspend fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }


    override fun isUserLoggedIn(): Boolean {
        return (auth.currentUser!=null)
    }

}