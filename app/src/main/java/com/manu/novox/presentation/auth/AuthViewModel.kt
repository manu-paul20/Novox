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
            AuthEvent.OpenChooseUserNameSheet -> {
                _state.update {
                    it.copy(
                        isBottomSheetOpen = true
                    )
                }
            }

            AuthEvent.CloseChooseUserNameSheet -> {
                _state.update {
                    it.copy(
                        isBottomSheetOpen = false
                    )
                }
            }

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
                if(_state.value.email.isBlank()||_state.value.pass.isBlank()){
                    return
                }
                handleAuthTask {
                    authRepository.signInWithEmail(
                        _state.value.email,
                        _state.value.pass
                    )
                }
            }

            is AuthEvent.SignInWithGoogle -> {
                handleAuthTask { authRepository.signInWithGoogle(event.context) }
            }

            AuthEvent.SignUpWithEmailPass -> {
                if(_state.value.email.isBlank()||_state.value.pass.isBlank()){
                    return
                }
                handleAuthTask {
                    authRepository.signUpWithEmail(
                        _state.value.email,
                        _state.value.pass
                    )
                }
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
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true) }
                    try {
                        authRepository.resetPassword(_state.value.email)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                message = "Password reset email sent to ${_state.value.email}"
                            )
                        }
                    }catch (e: Exception){
                       _state.update {
                           it.copy(
                               isLoading = false,
                               message = e.localizedMessage?:"Unknown Error"
                           )
                       }
                    }
                }
            }
        }

    }

    private fun handleAuthTask(
        task: suspend () -> Unit
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
                _state.update { it.copy(isLoading = false) }
                _authEffect.emit(AuthEffect.NavigateToHome)
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