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
    val name = "Manu Paul"
    val userName = "azaral"
    val email = "manupaul535@gmail.com"
    val profilePic =
        "data:image/jpeg;base64,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"

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
                    model = profilePic,
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
                onClick = {}
            ) {
                Text("CLOSE")
            }
        }

        OutlinedButton(
            onClick = onDismiss
        ) {
            Text("CLOSE")
        }

    }

}