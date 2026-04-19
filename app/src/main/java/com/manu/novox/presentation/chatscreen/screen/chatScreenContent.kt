package com.manu.novox.presentation.chatscreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.manu.novox.presentation.chatscreen.ChatScreenState

@Composable
fun ChatScreenContent(
    modifier: Modifier,
    onClickChatImage: (String) -> Unit,
    state: ChatScreenState
) {

    val lazyState = rememberLazyListState()
    LaunchedEffect(state.messagesList.size) {
        if (state.messagesList.isNotEmpty()) {
            lazyState.animateScrollToItem(state.messagesList.size - 1)
        }
    }

    LazyColumn(
        state = lazyState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .background(Color(0xFFF9F6F0)),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            key = {it.messageId},
            items = state.messagesList
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (it.senderUserName == state.userName) Arrangement.Start else Arrangement.End
            ) {
                MessageBubble(
                    message = it,
                    onClickImage = {
                        onClickChatImage(it.image)
                    },
                    settings = state.settings
                )
            }
        }
    }
}
