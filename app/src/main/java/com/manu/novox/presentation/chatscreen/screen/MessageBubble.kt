package com.manu.novox.presentation.chatscreen.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.manu.novox.core.utils.toColor
import com.manu.novox.core.utils.toFontFamily
import com.manu.novox.core.utils.toFontStyle
import com.manu.novox.data.local.entity.Message
import com.manu.novox.data.local.entity.UserSettings
import com.manu.novox.presentation.chatscreen.ChatScreenState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun MessageBubble(
    message: Message,
    onClickImage:()-> Unit,
    settings: UserSettings
) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
        val instant = Instant.ofEpochMilli(message.timeStamp)

    Log.i("user name",settings.userName)
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if(settings.userName == message.senderUserName){
                            settings.messageBoxColor.toColor()
                        }else{
                            settings.friendMessageBoxColor.toColor()
                        }
                    )
                ) {
                    if(message.image.isNotBlank()){
                        AsyncImage(
                            model = message.image,
                            modifier = Modifier
                                .clickable{onClickImage()}
                                .size(100.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentDescription = "image",
                            contentScale = ContentScale.Crop
                        )
                    }
                    Row(
                        modifier = Modifier.align(Alignment.End),
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        if (message.text.isNotBlank()) {
                            Text(
                                modifier = Modifier.padding(5.dp).weight(1f,fill = false),
                                text = message.text,
                                fontStyle = settings.fontStyle.toFontStyle(),
                                fontFamily = settings.fontFamily.toFontFamily(),
                                color = settings.textColor.toColor(),
                                fontSize = settings.textFontSize.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        Text(
                            fontSize = 10.sp,
                            fontStyle = settings.fontStyle.toFontStyle(),
                            fontFamily = settings.fontFamily.toFontFamily(),
                            color = settings.textColor.toColor(),
                            text = formatter.format(instant),
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }


}