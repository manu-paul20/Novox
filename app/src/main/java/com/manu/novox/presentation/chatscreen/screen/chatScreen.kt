
package com.manu.novox.presentation.chatscreen.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.manu.novox.R
import com.manu.novox.presentation.chatscreen.ChatScreenEffects
import com.manu.novox.presentation.chatscreen.ChatScreenEvents
import com.manu.novox.presentation.chatscreen.ChatScreenViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    userName: String,
    profilePhoto: String,
    name: String,
    viewModel: ChatScreenViewmodel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val context = LocalContext.current
    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){uri: Uri? ->
        uri?.let {
            onEvent(ChatScreenEvents.SetImageUrl(it.toString()))
        }
    }
    LaunchedEffect(true) {
        viewModel.effect.collect { effects ->
            when (effects) {
                is ChatScreenEffects.ShowToast -> {
                    Toast.makeText(context, effects.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        onEvent(ChatScreenEvents.SetUserDetails(userName,profilePhoto))
        viewModel.loadAllMessages(userName)
    }
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF3EFE7)
                ),
                title = {Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        shape = CircleShape
                    ){
                        AsyncImage(
                            contentScale = ContentScale.Crop,
                            model = profilePhoto,
                            error = painterResource(R.drawable.defaultprofile),
                            contentDescription = "user profile photo"
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = name,
                        modifier = Modifier.weight(1f),
                        fontSize = state.value.settings.appFontSize.sp
                    )
                }}
            )
        },
        bottomBar = {
            Column(modifier = Modifier.background(Color(0xFFF3EFE7))){
                if(state.value.imageUrl.isNotBlank()){
                    BadgedBox(
                        modifier = Modifier.padding(10.dp),
                        badge = {
                            IconButton(
                                onClick = {onEvent(ChatScreenEvents.ClearImage)}
                            ) {
                                Icon(Icons.Default.Close,"clear image")
                            }
                        },
                        content = {
                                AsyncImage(
                                    model = state.value.imageUrl,
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "selected image",
                                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                                )

                        }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(5.dp)
                ) {
                    IconButton(
                        onClick = { activityLauncher.launch("image/*") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto,
                            contentDescription = "add photo"
                        )
                    }
                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(50),
                        value = state.value.message,
                        onValueChange = { onEvent(ChatScreenEvents.SetMessage(it)) }
                    )
                   AnimatedVisibility(
                       visible = state.value.message.isNotBlank() || state.value.imageUrl.isNotBlank(),
                       enter = slideInHorizontally(),
                       exit = slideOutHorizontally()
                   ) {
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
            }
        }
    ) {innerPadding->
        ChatScreenContent(
            modifier = Modifier.padding(innerPadding),
            state = state.value,
            onClickChatImage = {
                onEvent(ChatScreenEvents.ShowImageInFullScreen(it))
            }
        )
    }
    if (state.value.displayImageUrl.isNotBlank()) {
        ImageShow(
            imageUrl = state.value.displayImageUrl,
            onClose = {
                onEvent(ChatScreenEvents.CloseFullScreenImage)
            },
            onDownload = {
                onEvent(ChatScreenEvents.DownloadImage(context))
            }
        )
    }
}
