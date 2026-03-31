package com.manu.novox.presentation.chatscreen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.manu.novox.data.local.entity.Message
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun MessageBubble(
    message: Message
) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
        val instant = Instant.ofEpochMilli(message.timeStamp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (message.senderUserName == "Manu Paul") Arrangement.Start else Arrangement.End
        ) {

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Gray)
                ) {
                    if(message.image.isNotBlank()){
                        AsyncImage(
                            model = message.image,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentDescription = "image",
                            contentScale = ContentScale.Crop
                        )
                    }
                    Row {
                        Text(
                            modifier = Modifier.padding(5.dp),
                            text=message.text,
                            textAlign = TextAlign.Start
                        )
                        Spacer(Modifier.width(20.dp))
                        Text(
                            text = formatter.format(instant),
                            textAlign = TextAlign.End
                        )
                    }
                }

        }
}