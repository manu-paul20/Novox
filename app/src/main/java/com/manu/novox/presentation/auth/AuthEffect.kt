package com.manu.novox.presentation.auth

sealed interface AuthEffect {
    object NavigateToHome: AuthEffect
    data class ShowToast(val message: String): AuthEffect
    data class ShowErrorPopUp(val message: String): AuthEffect
}