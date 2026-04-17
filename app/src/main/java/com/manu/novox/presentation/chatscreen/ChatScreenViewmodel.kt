package com.manu.novox.presentation.chatscreen

import android.app.DownloadManager
import android.app.DownloadManager.Request
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manu.novox.core.utils.getImageExtension
import com.manu.novox.domain.repository.ChatRepository
import com.manu.novox.domain.repository.SettingsRepository
import com.manu.novox.presentation.chatscreen.ChatScreenEffects.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
class ChatScreenViewmodel @Inject constructor(
    private val chatRepository: ChatRepository,
     settingsRepository: SettingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ChatScreenState())
    private val _effect = MutableSharedFlow<ChatScreenEffects>()
    val effect = _effect.asSharedFlow()

    private val settings = settingsRepository.getUserSettings()

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

    val state = combine(_state,settings){state,settings->
        state.copy(
            settings = settings
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ChatScreenState())

    fun onEvent(event: ChatScreenEvents){
        when(event) {
            ChatScreenEvents.ClearChat -> {
                _state.update { it.copy(messagesList = emptyList()) }
                viewModelScope.launch {
                    chatRepository.clearChat(_state.value.userName)
                }

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
                }
                _state.update { it.copy(
                    message = "",
                    imageUrl = ""
                ) }
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


            is ChatScreenEvents.SetImageUrl -> {
                _state.update { it.copy(imageUrl = event.url) }
            }

            ChatScreenEvents.ClearImage -> {
                _state.update { it.copy(imageUrl = "") }
            }

            is ChatScreenEvents.ShowImageInFullScreen -> {
                _state.update { it.copy(displayImageUrl = event.imageUrl) }
            }

            ChatScreenEvents.CloseFullScreenImage -> {
                _state.update { it.copy(displayImageUrl = "") }
            }

            is ChatScreenEvents.DownloadImage -> {
                val imageExtension = getImageExtension(state.value.displayImageUrl)
                val fileName = "Image${System.currentTimeMillis()}.$imageExtension"
                val request = Request(_state.value.displayImageUrl.toUri())
                    .setTitle(fileName)
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
                    .setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                val downloadManager = event.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                downloadManager.enqueue(request)
                emitEffect(ShowToast("Downloading image.."))

            }

            ChatScreenEvents.ToggleDropDown -> {
                _state.update { it.copy(isDropDownOpen = !it.isDropDownOpen) }
            }
        }

    }

    fun emitEffect(effect: ChatScreenEffects){
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

}