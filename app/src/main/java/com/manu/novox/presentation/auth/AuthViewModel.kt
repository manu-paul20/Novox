package com.manu.novox.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.novox.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    private val _authEffect = MutableSharedFlow<AuthEffect>()
    val authEffect = _authEffect.asSharedFlow()

    init {
        if (authRepository.isUserLoggedIn()) {
            viewModelScope.launch {
                _authEffect.emit(AuthEffect.NavigateToHome)
            }
        }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SetEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            AuthEvent.SetModeToSignIn -> {
                _state.update {
                    it.copy(
                        isSignInMode = true
                    )
                }
            }

            AuthEvent.SetModeToSignUp -> {
                _state.update {
                    it.copy(
                        isSignInMode = false
                    )
                }
            }

            is AuthEvent.SetPassword -> {
                _state.update {
                    it.copy(
                        pass = event.password
                    )
                }
            }

            AuthEvent.SignInWithEmailPass -> {

                handleAuthTask (
                    task = {
                    if(_state.value.email.isBlank()||_state.value.pass.isBlank()){
                        _authEffect.emit(AuthEffect.ShowToast("Email and password cannot be empty "))
                        return@handleAuthTask
                    }
                    authRepository.signInWithEmail(
                        _state.value.email,
                        _state.value.pass
                    )
                })
            }

            is AuthEvent.SignInWithGoogle -> {
                handleAuthTask (task = { authRepository.signInWithGoogle(event.context) })
            }

            AuthEvent.SignUpWithEmailPass -> {
                handleAuthTask(
                    task = {
                        if(_state.value.email.isBlank()||_state.value.pass.isBlank()){
                            _authEffect.emit(AuthEffect.ShowToast("Email and password cannot be empty "))
                            return@handleAuthTask
                        }
                        authRepository.signUpWithEmail(
                            _state.value.email,
                            _state.value.pass
                        )
                    },
                    onSuccess = {
                        _authEffect.emit(AuthEffect.NavigateToAccountCreation)
                    }
                )
            }

            AuthEvent.ResetMessageDialog -> {
                _state.update {
                    it.copy(message = "")
                }
            }

            AuthEvent.SendPasswordResetEmail -> {
                if (
                    _state.value.email.isBlank() ||
                    _state.value.isLoading
                ) {
                    return
                }
                handleAuthTask(
                    task = {
                        authRepository.resetPassword(_state.value.email)
                    },
                    onSuccess = {
                        _state.update { it.copy(
                            isLoading = false,
                            message = "Password reset email sent to ${_state.value.email}"
                        ) }
                    }
                )
            }
        }

    }

    private fun handleAuthTask(
        task: suspend () -> Unit,
        onSuccess:(suspend ()-> Unit)? = null
    ) {
        if (_state.value.isLoading) {
            return
        }
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                task()
               if(onSuccess==null){
                   _state.update { it.copy(isLoading = false) }
                   _authEffect.emit(AuthEffect.NavigateToHome)
               }else{
                   onSuccess()
               }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        message = e.localizedMessage ?: "Unknown Error"
                    )
                }
            }
        }
    }
}