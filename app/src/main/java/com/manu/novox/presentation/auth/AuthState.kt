package com.manu.novox.presentation.auth

data class AuthState(
    val email: String = "",
    val pass: String = "",
    val authMode: AuthMode = AuthMode.SignIn,
    val isLoading: Boolean = false
)