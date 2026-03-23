package com.manu.novox.presentation.accountCreation
sealed interface AccountCreationEffect{
    object NavigateToChatList: AccountCreationEffect

    data class ShowToast(val message: String): AccountCreationEffect
}