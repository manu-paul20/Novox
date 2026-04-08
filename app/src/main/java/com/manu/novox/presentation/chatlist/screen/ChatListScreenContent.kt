package com.manu.novox.presentation.chatlist.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil3.compose.AsyncImage
import com.manu.novox.R
import com.manu.novox.presentation.chatlist.ChatListState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun ChatListContent(
    modifier: Modifier,
    state: ChatListState,
    onClickChat: (userName:String, profilePhoto:String,name: String) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = state.interactedUser,
            key = { it.userName }
        ) {

            val instant = Instant.ofEpochMilli(it.lastInteracted)
            Row(
                modifier = Modifier
                    .clickable { onClickChat(
                        it.userName,
                        it.profilePhoto,
                        it.name
                    ) }
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = it.profilePhoto,
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
                        text = it.name,
                        fontStyle = state.fontStyle,
                        fontSize = state.fontSize.sp,
                        fontFamily = state.fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(2.dp))

                    if(it.lastMessage.isNotBlank()){
                        Text(
                            text = it.lastMessage,
                            maxLines = 1,
                            fontStyle = state.fontStyle,
                            fontSize = state.fontSize.sp,
                            fontFamily = state.fontFamily,
                            overflow = TextOverflow.Ellipsis,
                            color = Color(0xFF49454F)
                        )
                    }
                }

                Text(
                    text = formatter.format(instant),
                    fontStyle = state.fontStyle,
                    fontSize = state.fontSize.sp,
                    fontFamily = state.fontFamily,
                    color = Color(0XFF79747E)
                )
            }
            HorizontalDivider(color = Color(0XFFE4E2DB))
        }
    }

}
