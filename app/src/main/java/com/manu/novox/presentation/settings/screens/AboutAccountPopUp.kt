package com.manu.novox.presentation.settings.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.manu.novox.R
import com.manu.novox.core.utils.toFontFamily
import com.manu.novox.core.utils.toFontStyle
import com.manu.novox.presentation.settings.SettingsState

@Composable
fun AboutAccountPopUp(
    state: SettingsState,
    onDismiss:()-> Unit
) {

    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = {}
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = state.profilePic,
                    contentDescription = "profile pic",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    error = painterResource(R.drawable.defaultprofile)
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = state.name,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(20.dp))
                TextComponents(
                    text1 = "User Name",
                    text2 = state.userName,
                    fontFamily = state.fontFamily.toFontFamily(),
                    fontStyle = state.fontStyle.toFontStyle()
                )
                TextComponents(
                    text1 = "Email",
                    text2 = state.email,
                    fontFamily = state.fontFamily.toFontFamily(),
                    fontStyle = state.fontStyle.toFontStyle()
                )
                HorizontalDivider()
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                onClick = onDismiss
            ) {
                Text("CLOSE")
            }
        }


    }

}