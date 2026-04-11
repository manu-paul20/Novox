package com.manu.novox.presentation.auth

sealed interface AuthEffect {
    object NavigateToChatList: AuthEffect

    object NavigateToAccountCreation: AuthEffect
    data class ShowToast(val message: String): AuthEffect
}