package com.manu.novox.presentation.auth

import android.content.Context

sealed interface AuthEvent{

    object ResetMessageDialog: AuthEvent

    data class SetEmail(val email: String): AuthEvent
    data class SetPassword(val password: String): AuthEvent

    object SignInWithEmailPass: AuthEvent
    object SignUpWithEmailPass: AuthEvent
    data class SignInWithGoogle(val context: Context): AuthEvent

    object SetModeToSignIn: AuthEvent
    object SetModeToSignUp: AuthEvent

    object SendPasswordResetEmail: AuthEvent
}