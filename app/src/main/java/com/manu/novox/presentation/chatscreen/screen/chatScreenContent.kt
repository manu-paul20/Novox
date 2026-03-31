package com.manu.novox.presentation.chatscreen.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manu.novox.data.local.entity.Message

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ChatScreenContent() {

    val lazyState = rememberLazyListState()
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            lazyState.animateScrollToItem(messages.size - 1)
        }
    }

    LazyColumn(
        state = lazyState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            key = {},
            items =
        ){
            MessageBubble()
        }
    }
}
