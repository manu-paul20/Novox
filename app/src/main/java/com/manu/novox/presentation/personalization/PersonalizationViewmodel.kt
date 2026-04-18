package com.manu.novox.presentation.personalization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.novox.data.local.entity.UserSettings
import com.manu.novox.domain.repository.SettingsRepository
import com.manu.novox.presentation.personalization.PersonalizationEffects.ShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalizationViewmodel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PersonalizationState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<PersonalizationEffects>()
    val effect = _effects.asSharedFlow()

    fun onEvent(events: PersonalizationEvents) {
        when (events) {
            PersonalizationEvents.Close -> {
                emitEffect(PersonalizationEffects.NavigateToSettings)
            }

            is PersonalizationEvents.OnFontFamilyDropDownChange -> {
                _state.value = _state.value.copy(
                    isFontFamilyDropDownOpen = events.value
                )
            }

            is PersonalizationEvents.OnFontFamilyChange -> {
                _state.value = _state.value.copy(
                    fontFamily = events.fontFamily
                )
            }

            is PersonalizationEvents.OnTextFontSizeChange -> {
                _state.update {
                    it.copy(
                        textFontSize = events.fontSize
                    )
                }
            }

            is PersonalizationEvents.OnAppFontSizeChange -> {
                _state.update {
                    it.copy(
                        appFontSize = events.fontSize
                    )
                }
            }

            is PersonalizationEvents.OnFontStyleChange -> {
                _state.update {
                    it.copy(
                        fontStyle = events.fontStyle
                    )
                }
            }

            is PersonalizationEvents.OnFriendMessageBoxColorChange -> {
                _state.update {
                    it.copy(
                        friendMessageBoxColor = events.color
                    )
                }
            }

            is PersonalizationEvents.OnMessageBoxColorChange -> {
                _state.update {
                    it.copy(
                        messageBoxColor = events.color
                    )
                }
            }

            is PersonalizationEvents.OnTextColorChange -> {
                _state.update {
                    it.copy(
                        textColor = events.color
                    )
                }
            }

            PersonalizationEvents.Save -> {
                viewModelScope.launch {
                    val settings = _state.value.run {
                        UserSettings().copy(
                            fontFamily = fontFamily,
                            fontStyle = fontStyle,
                            textFontSize = textFontSize,
                            appFontSize = appFontSize,
                            messageBoxColor = messageBoxColor,
                            textColor = textColor,
                            friendMessageBoxColor = friendMessageBoxColor
                        )
                    }
                    settingsRepository.updateSettings(settings)
                    emitEffect(ShowToast("Settings Saved"))
                    emitEffect(PersonalizationEffects.NavigateToSettings)
                }
            }

            is PersonalizationEvents.OnFriendMessageBoxColorDropDown -> {
                _state.value = _state.value.copy(
                    isFriendsMessageBoxColorDropDownOpen = events.value
                )
            }

            is PersonalizationEvents.OnMessageBoxColorDropDown -> {
                _state.value = _state.value.copy(
                    isMyMessageBoxColorDropDownOpen = events.value
                )
            }

            is PersonalizationEvents.OnTextColorDropDown -> {
                _state.value = _state.value.copy(
                    isTextColorDropDownOpen = events.value
                )
            }
        }
    }

    fun emitEffect(effects: PersonalizationEffects) {
        viewModelScope.launch { _effects.emit(effects) }
    }
}