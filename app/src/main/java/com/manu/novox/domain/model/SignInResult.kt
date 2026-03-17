package com.manu.novox.domain.model

data class SignInResult(
    val isSignInSuccessful: Boolean,
    val errorMessage: String?
)
