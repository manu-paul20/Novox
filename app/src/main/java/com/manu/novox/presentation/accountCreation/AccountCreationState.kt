package com.manu.novox.presentation.accountCreation

data class AccountCreationState(
    val name: String = "",
    val userName: String = "",
    val error: String = "",
    val isLoading: Boolean = false
)