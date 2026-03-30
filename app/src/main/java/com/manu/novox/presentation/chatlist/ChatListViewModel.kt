package com.manu.novox.presentation.chatlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.novox.core.utils.toFontFamily
import com.manu.novox.core.utils.toFontStyle
import com.manu.novox.domain.repository.AccountRepository
import com.manu.novox.domain.repository.InteractedUserRepository
import com.manu.novox.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val interactedUserRepo: InteractedUserRepository,
    private val accountRepository: AccountRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            val currentUser = accountRepository.getAccountDetails()
            if (currentUser == null) {
                emitEffect(ChatListEffects.NavigateToAccountCreation)
            }
        }
    }
    private val _state = MutableStateFlow(ChatListState())
    private val interactedUsers = interactedUserRepo.getAllUser()
    private val _settings = settingsRepository.getUserSettings()


    private val _effect = MutableSharedFlow<ChatListEffects>()
    val effect = _effect.asSharedFlow()

    val state = combine(_state, interactedUsers, _settings) { state, users, settings ->
        val userList = users.filter {
            it.name.contains(state.searchQuery.replace(" ", ""), ignoreCase = true)
        }
        state.copy(
            interactedUser = userList,
            fontSize = settings.appFontSize,
            fontFamily = settings.fontFamily.toFontFamily(),
            fontStyle = settings.fontStyle.toFontStyle()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ChatListState())

    fun onEvent(event: ChatListEvents) {
        when (event) {
            ChatListEvents.CloseLocalSearchBox -> {
                _state.update {
                    it.copy(
                        isLocalSearchEnabled = false,
                        searchQuery = ""
                    )
                }
            }

            ChatListEvents.CloseNewUserSheet -> {
                _state.update {
                    it.copy(
                        isBottomSheetOpen = false,
                        newUserName = ""
                    )
                }
            }

            is ChatListEvents.OnNewUserNameChange -> {
                _state.update { it.copy(newUserName = event.query) }
            }

            is ChatListEvents.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
            }

            ChatListEvents.OpenLocalSearchBox -> {
                _state.update { it.copy(isLocalSearchEnabled = true) }
            }

            ChatListEvents.OpenNewUserSheet -> {
                _state.update { it.copy(isBottomSheetOpen = true) }
            }

            is ChatListEvents.SearchNewUser -> {
                if (_state.value.newUserName.isBlank()) {
                    return
                }
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isSearchingNewUser = true,
                            searchingError = ""
                        )
                    }
                    try {
                        val user = interactedUserRepo.searchUser(_state.value.newUserName)
                        _state.update {
                            it.copy(
                                searchingError = "",
                                searchResult = user,
                                isSearchingNewUser = false
                            )
                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                searchingError = e.localizedMessage ?: "Something went wrong",
                                isSearchingNewUser = false
                            )
                        }
                    }
                }
            }

            ChatListEvents.AddUser -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    try {
                        interactedUserRepo.addNewUserToChatList(_state.value.searchResult!!)
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isBottomSheetOpen = false
                            )
                        }
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isBottomSheetOpen = false,
                                error = e.localizedMessage ?: "Something went wrong"
                            )
                        }
                    }
                }
            }

            ChatListEvents.CloseExitDialog -> {
                _state.update {
                    it.copy(isExitDialogOpen = false)
                }
            }
            ChatListEvents.OpenExitDialog -> {
                _state.update {
                    it.copy(isExitDialogOpen = true)
                }
            }
        }
    }

    private fun emitEffect(effect: ChatListEffects) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}