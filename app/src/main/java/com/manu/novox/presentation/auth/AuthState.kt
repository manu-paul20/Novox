package com.manu.novox.presentation.auth

data class AuthState(
    val email: String = "",
    val pass: String = "",
    val isSignInMode: Boolean = true,
    val isLoading: Boolean = false,
    val message: String = ""
)