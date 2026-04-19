package com.manu.novox.presentation.personalization

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.novox.data.local.entity.UserSettings
import com.manu.novox.domain.repository.SettingsRepository
import com.manu.novox.presentation.personalization.PersonalizationEffects.ShowToast
import com.manu.novox.presentation.personalization.screens.PersonalizationScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalizationViewmodel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PersonalizationState())
    private val _settings = MutableStateFlow(UserSettings())


    val state = combine(_state,_settings){state,settings->
        state.copy(
            settings = settings
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PersonalizationState())

    private val _effects = MutableSharedFlow<PersonalizationEffects>()
    val effect = _effects.asSharedFlow()

    fun loadSettings(){
        viewModelScope.launch {
            val setting = settingsRepository.getUserSettings().firstOrNull()
            setting?.let {
                _settings.update {
                    setting.copy()
                }
            }
        }
    }

    fun onEvent(events: PersonalizationEvents) {
        when (events) {
            PersonalizationEvents.Close -> {
                emitEffect(PersonalizationEffects.NavigateToSettings)
            }

            is PersonalizationEvents.OnFontFamilyDropDownChange -> {
               _state.update {
                   it.copy(
                       isFontFamilyDropDownOpen = events.value
                   )
               }
            }

            is PersonalizationEvents.OnFontFamilyChange -> {
                _settings.update {
                    it.copy(
                        fontFamily = events.fontFamily
                    )
                }
            }

            is PersonalizationEvents.OnTextFontSizeChange -> {
                _settings.update {
                    it.copy(
                        textFontSize = events.fontSize
                    )
                }
            }

            is PersonalizationEvents.OnAppFontSizeChange -> {
                _settings.update {
                    it.copy(
                        appFontSize = events.fontSize
                    )
                }
            }

            is PersonalizationEvents.OnFontStyleChange -> {
                _settings.update {
                    it.copy(
                        fontStyle = events.fontStyle
                    )
                }
            }

            is PersonalizationEvents.OnFriendMessageBoxColorChange -> {
                _settings.update {
                    it.copy(
                        friendMessageBoxColor = events.color
                    )
                }
                _state.update {
                    it.copy(
                        isFriendsMessageBoxColorDropDownOpen = false
                    )
                }
            }

            is PersonalizationEvents.OnMessageBoxColorChange -> {
                _settings.update { it.copy(
                    messageBoxColor = events.color,
                ) }
                _state.update {
                    it.copy(
                        isMyMessageBoxColorDropDownOpen = false
                    )
                }
            }

            is PersonalizationEvents.OnTextColorChange -> {
                _settings.update { it.copy(
                    textColor = events.color,
                ) }
                _state.update {
                    it.copy(
                        isTextColorDropDownOpen = false
                    )
                }
            }

            PersonalizationEvents.Save -> {
                viewModelScope.launch {
                    settingsRepository.updateSettings(_settings.value)
                    emitEffect(ShowToast("Settings Saved"))
                }
            }

            is PersonalizationEvents.OnFriendMessageBoxColorDropDown -> {
                _state.update {
                    it.copy(isFriendsMessageBoxColorDropDownOpen = events.value)
                }
            }

            is PersonalizationEvents.OnMessageBoxColorDropDown -> {
                _state.update {
                    it.copy(isMyMessageBoxColorDropDownOpen = events.value)
                }
            }

            is PersonalizationEvents.OnTextColorDropDown -> {
                _state.update {
                    it.copy(isTextColorDropDownOpen = events.value)
                }
            }
        }
    }

    fun emitEffect(effects: PersonalizationEffects) {
        viewModelScope.launch { _effects.emit(effects) }
    }
}