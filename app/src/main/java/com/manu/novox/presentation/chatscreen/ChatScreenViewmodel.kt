package com.manu.novox.presentation.chatscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.novox.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewmodel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ChatScreenState())
    private val _effect = MutableSharedFlow<ChatScreenEffects>()
    val effect = _effect.asSharedFlow()

    private var getMessageJob: Job? = null
    fun loadAllMessages(userName: String){
        getMessageJob?.cancel()
        getMessageJob = viewModelScope.launch {
            _state.update { it.copy(userName = userName) }

            chatRepository.getAllMessages(userName).collect { messages ->
                _state.update { it.copy(messagesList = messages) }
            }
        }
    }

    val state = _state.asStateFlow()

    fun onEvent(event: ChatScreenEvents){
        when(event) {
            ChatScreenEvents.ClearChat -> {
                _state.update { it.copy(messagesList = emptyList()) }
            }
            ChatScreenEvents.SendMessage -> {
                if(_state.value.message.isBlank() && _state.value.imageUrl.isBlank()){
                    return
                }
                viewModelScope.launch {
                    chatRepository.addMessageToChat(
                        receiverUserName = _state.value.userName,
                        text = _state.value.message,
                        imageUrl = _state.value.imageUrl,
                        onProgress = null
                    )
                    _state.update { it.copy(message = "") }
                }
            }
            is ChatScreenEvents.SetMessage -> {
                _state.update { it.copy(message = event.message) }
            }

            is ChatScreenEvents.SetUserDetails -> {
                _state.update {
                    it.copy(
                        userName = event.userName,
                        profilePhoto = event.profilePhoto
                    )
                }
            }

            ChatScreenEvents.ToggleTyping -> {
                _state.update {
                    it.copy(
                        isTyping =  true
                    )
                }
            }
        }

    }

    fun emitEffect(effect: ChatScreenEffects){
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

}