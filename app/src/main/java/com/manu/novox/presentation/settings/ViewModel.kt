package com.manu.novox.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.novox.domain.repository.AccountRepository
import com.manu.novox.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    settingsRepository: SettingsRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _settings = settingsRepository.getUserSettings()
    private val _state = MutableStateFlow(SettingsState())

    fun getAccountDetails() {
        viewModelScope.launch {
            val account = accountRepository.getAccountDetails()!!
            _state.update {
                it.copy(
                    userName = account.userName,
                    name = account.name,
                    email = account.email,
                    profilePic = account.profilePhoto
                )
            }
        }
    }

    val state = _state.combine(_settings) { state, settings ->
        state.copy(
            fontFamily = settings.fontFamily,
            appFontSize = settings.appFontSize,
            fontStyle = settings.fontStyle
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsState())

    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.CloseAboutAccountPopUp -> {
                _state.update { it.copy(isAboutAccountPopUpOpen = false) }
            }

            SettingsEvent.CloseAboutAppPopUp -> {
                _state.update { it.copy(isAboutAppPopUpOpen = false) }
            }

            SettingsEvent.OpenAboutAccountPopUp -> {
                _state.update { it.copy(isAboutAccountPopUpOpen = true) }
            }

            SettingsEvent.OpenAboutAppPopUp -> {
                _state.update { it.copy(isAboutAppPopUpOpen = true) }
            }
        }
    }
}