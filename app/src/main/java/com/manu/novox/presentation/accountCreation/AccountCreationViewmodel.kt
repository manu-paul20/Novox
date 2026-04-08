package com.manu.novox.presentation.accountCreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.novox.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountCreationViewmodel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AccountCreationState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AccountCreationEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: AccountCreationEvent) {
        when(event){
            AccountCreationEvent.CreateAccount -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        accountRepository.createAccount(
                            name = _state.value.name,
                            userName = _state.value.userName.lowercase()
                        )
                        _state.update { it.copy(isLoading = false) }
                        emmitEffect(AccountCreationEffect.NavigateToChatList)
                    }catch (e: Exception){
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = e.localizedMessage?:"Unknown Error"
                            )
                        }
                    }
                }
            }
            AccountCreationEvent.ResetErrorMessage -> {
                _state.update { it.copy(error = "") }
            }
            is AccountCreationEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }
            is AccountCreationEvent.SetUserName -> {
                _state.update { it.copy(userName = event.userName) }
            }
        }
    }

    private fun emmitEffect(effect: AccountCreationEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}