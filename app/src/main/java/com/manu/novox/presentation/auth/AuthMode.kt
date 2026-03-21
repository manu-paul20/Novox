package com.manu.novox.presentation.auth

sealed interface AuthMode{
    object SignIn:AuthMode
    object SignUp:AuthMode
}