package com.manu.novox.presentation.accountCreation

sealed interface AccountCreationEvent {
    data class SetName(val name: String) : AccountCreationEvent
    data class SetUserName(val userName: String): AccountCreationEvent

    object CreateAccount: AccountCreationEvent

    object ResetErrorMessage: AccountCreationEvent
}