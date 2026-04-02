
package com.manu.novox.presentation.chatscreen.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.manu.novox.data.local.entity.Message
import com.manu.novox.presentation.chatlist.ChatListViewModel
import com.manu.novox.presentation.chatscreen.ChatScreenEvents
import com.manu.novox.presentation.chatscreen.ChatScreenViewmodel

@Composable
fun ChatScreen(
    userName: String,
    profilePhoto: String,
    viewModel: ChatScreenViewmodel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    LaunchedEffect(Unit) {
        onEvent(ChatScreenEvents.SetUserDetails(userName,profilePhoto))
        viewModel.loadAllMessages(userName)
    }
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = profilePhoto,
                    contentDescription = "user profile photo"
                )
                Spacer(Modifier.width(10.dp))
                Text(userName)
            }
        },
        bottomBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ){
                OutlinedTextField(
                    shape = RoundedCornerShape(20.dp),
                    value = state.value.message,
                    onValueChange = { onEvent(ChatScreenEvents.SetMessage(it)) }
                )
                IconButton(
                    onClick = {
                        onEvent(ChatScreenEvents.SendMessage)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "send message"
                    )
                }
            }
        }
    ) {innerPadding->
        ChatScreenContent(
            modifier = Modifier.padding(innerPadding),
            state = state.value,
            onEvent = onEvent
        )
    }
}
