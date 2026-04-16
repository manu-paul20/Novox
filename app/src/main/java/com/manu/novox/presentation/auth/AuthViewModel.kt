package com.manu.novox.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.novox.data.local.entity.UserSettings
import com.manu.novox.domain.repository.AccountRepository
import com.manu.novox.domain.repository.AuthRepository
import com.manu.novox.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountRepository: AccountRepository,
    private val settingsRepo: SettingsRepository
): ViewModel(){
    private val _state = MutableStateFlow(AuthState(isUserLoggedIn = authRepository.isUserLoggedIn()))
    val state = _state.asStateFlow()

    private val _authEffect = MutableSharedFlow<AuthEffect>()
    val authEffect = _authEffect.asSharedFlow()

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
                if(_state.value.email.isBlank()||_state.value.pass.isBlank()){
                    emitEffect(AuthEffect.ShowToast("Email and password cannot be empty "))
                    return
                }
                handleAuthTask (
                    task = {

                    authRepository.signInWithEmail(
                        _state.value.email,
                        _state.value.pass
                    )

                })
            }

            is AuthEvent.SignInWithGoogle -> {
                handleAuthTask (
                    task = {
                    authRepository.signInWithGoogle(event.context)
                }
                )
            }

            AuthEvent.SignUpWithEmailPass -> {
                if(_state.value.email.isBlank()||_state.value.pass.isBlank()){
                    emitEffect(AuthEffect.ShowToast("Email and password cannot be empty "))
                    return
                }
                handleAuthTask(
                    task = {
                        authRepository.signUpWithEmail(
                            _state.value.email,
                            _state.value.pass
                        )
                    },
                    onSuccess = {
                        _state.update { it.copy(isLoading = false) }
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

                   val user  = accountRepository.getUserFromEmail()
                   _state.update { it.copy(isLoading = false) }
                   if(user != null){
                        accountRepository.saveUserToDB(user)
                       settingsRepo.updateKey(user.userName)
                       _authEffect.emit(AuthEffect.NavigateToChatList)
                   }else{
                       _authEffect.emit(AuthEffect.NavigateToAccountCreation)
                   }
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
    private fun emitEffect(effect: AuthEffect){
        viewModelScope.launch {
            _authEffect.emit(effect)
        }
    }
}