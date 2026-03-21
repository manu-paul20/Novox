package com.manu.novox.presentation.auth

sealed interface AuthEvent{
    object OpenChooseUserNamePopUp: AuthEvent
    object CloseChooseUserNamePopUp: AuthEvent

    data class SetEmail(val email: String): AuthEvent
    data class SetPassword(val password: String): AuthEvent

    object SignInWithEmailPass: AuthEvent
    object SignUpWithEmailPass: AuthEvent
    object SignInWithGoogle: AuthEvent

    object SetModeToSignIn: AuthEvent
    object SetModeToSignUp: AuthEvent
}