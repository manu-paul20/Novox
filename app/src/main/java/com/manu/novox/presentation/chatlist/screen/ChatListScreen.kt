package com.manu.novox.presentation.chatlist.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.manu.novox.R
import com.manu.novox.presentation.chatlist.ChatListEffects
import com.manu.novox.presentation.chatlist.ChatListEvents
import com.manu.novox.presentation.chatlist.ChatListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel = hiltViewModel(),
    onNavigateToAccountCreation: () -> Unit,
    onClickChat:(userName: String,profilePhoto: String,name: String)-> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    LaunchedEffect(true) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ChatListEffects.NavigateToAccountCreation -> {
                    onNavigateToAccountCreation()
                }
            }
        }
    }

    if (state.value.isBottomSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { onEvent(ChatListEvents.CloseNewUserSheet) },
        ) {
            ModalDrawerSheet(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add new user",
                        fontStyle = state.value.fontStyle,
                        fontSize = state.value.fontSize.sp,
                        fontFamily = state.value.fontFamily,
                    )
                    OutlinedTextField(
                        value = state.value.newUserName,
                        onValueChange = { onEvent(ChatListEvents.OnNewUserNameChange(it)) },
                        label = { Text("User Name") },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                    if (state.value.isSearchingNewUser) {
                        CircularProgressIndicator()
                    }
                    if (state.value.searchingError.isNotBlank()) {
                        Text(
                            text = state.value.searchingError,
                            color = Color.Red,
                            fontStyle = state.value.fontStyle,
                            fontSize = state.value.fontSize.sp,
                            fontFamily = state.value.fontFamily,
                        )
                    }
                    if (state.value.searchResult != null) {
                        Spacer(Modifier.height(10.dp))
                        ElevatedCard(
                            elevation = CardDefaults.elevatedCardElevation(20.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = state.value.searchResult!!.profilePhoto,
                                    contentDescription = "profile pic",
                                    error = painterResource(R.drawable.defaultprofile),
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(10.dp)
                                ) {
                                    Text(
                                        text = state.value.searchResult!!.userName,
                                        fontStyle = state.value.fontStyle,
                                        fontSize = state.value.fontSize.sp,
                                        fontFamily = state.value.fontFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(Modifier.height(2.dp))
                                    Text(
                                        text = state.value.searchResult!!.name,
                                        maxLines = 1,
                                        fontStyle = state.value.fontStyle,
                                        fontSize = state.value.fontSize.sp,
                                        fontFamily = state.value.fontFamily,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color(0xFF49454F)
                                    )
                                }
                                IconButton(
                                    onClick = {onEvent(ChatListEvents.AddUser)}
                                ) {
                                    Icon(Icons.Default.Add,"Add user")
                                }
                            }
                        }
                    }
                    Spacer(Modifier.height(30.dp))
                    OutlinedButton(
                        onClick = {

                            onEvent(ChatListEvents.SearchNewUser)

                        }
                    ) {
                        Text(text = "Search")
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color(0xFFF9F6F0),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF9F6F0)
                ),
                title = {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Chats",
                                fontSize = state.value.fontSize.sp,
                                fontFamily = state.value.fontFamily,
                                fontStyle = state.value.fontStyle,
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(
                                onClick = { onEvent(ChatListEvents.OpenLocalSearchBox) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search",
                                    tint = Color(0xFF49454F)
                                )
                            }
                            IconButton(
                                onClick = { onEvent(ChatListEvents.OpenNewUserSheet) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = "search user"
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = state.value.isLocalSearchEnabled,
                            enter = expandHorizontally() + fadeIn(),
                            exit = shrinkHorizontally() + fadeOut()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedTextField(
                                    value = state.value.searchQuery,
                                    onValueChange = { onEvent(ChatListEvents.OnSearchQueryChange(it)) },
                                    label = { Text("Search") },
                                    modifier = Modifier.weight(1f),
                                    singleLine = true,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                IconButton(
                                    onClick = { onEvent(ChatListEvents.CloseLocalSearchBox) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = "close search"
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    ){innerPadding->
        ChatListContent(
            modifier = Modifier.padding(innerPadding),
            state = state.value,
            onClickChat = onClickChat
        )
        if (state.value.isExitDialogOpen) {
            ExitDialog(
                onClose = { onEvent(ChatListEvents.CloseExitDialog) }
            )
        }
    }
    BackHandler {
        onEvent(ChatListEvents.OpenExitDialog)
    }
}