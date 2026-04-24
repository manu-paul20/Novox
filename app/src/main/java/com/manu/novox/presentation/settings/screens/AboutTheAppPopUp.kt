package com.manu.novox.presentation.settings.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.manu.novox.R
import com.manu.novox.core.utils.toFontFamily
import com.manu.novox.core.utils.toFontStyle
import com.manu.novox.presentation.settings.SettingsState

private var _fontFamily: FontFamily = FontFamily.Default
private var _fontStyle: FontStyle = FontStyle.Normal
@Composable
fun AboutTheAppPopUp(
    state: SettingsState,
    onDismiss: () -> Unit
) {
    val clipManager = LocalClipboardManager.current
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        _fontStyle = state.fontStyle.toFontStyle()
        _fontFamily = state.fontFamily.toFontFamily()
    }


    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "App logo"
                )

                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = "Novox",
                    fontFamily = _fontFamily,
                    fontStyle = _fontStyle,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Novox keeps your conversations flowing smoothly, even when your internet connection drops. Experience offline-first messaging wrapped in a beautifully comfortable design.",
                    fontStyle = _fontStyle,
                    fontFamily = _fontFamily,
                    modifier = Modifier.padding(5.dp)
                )
                Spacer(Modifier.height(20.dp))
                TextComponents(
                    text1 = "App version",
                    text2 = "1.0.0",

                    )
                TextComponents("Developer", "Manu Paul")
                TextComponents(
                    text1 = "Report a bug",
                    text2 = "manupaul535@gmail.com",
                    icon = Icons.Default.ContentCopy,
                    onClickIcon = {
                        clipManager.setText(AnnotatedString("manupaul200@gmail.com"))

                        Toast.makeText(
                            context,
                            "Copied!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                HorizontalDivider()
                Spacer(Modifier.height(30.dp))
                Text(
                    text = "Socials",
                    fontFamily = _fontFamily,
                    fontStyle = _fontStyle,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.insta),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clickable(onClick = {
                                uriHandler.openUri("https://www.instagram.com/_azaral?igsh=MTNrbXQ2bHA4dWFpdQ==")
                            })

                    )
                    Spacer(Modifier.width(10.dp))
                    Image(
                        painter = painterResource(R.drawable.discord),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clickable(onClick = {
                                uriHandler.openUri("https://discord.gg/SrR2gnfU")
                            })
                    )
                }
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

@Composable
fun TextComponents(
    text1: String,
    text2: String,
    icon: ImageVector? = null,
    onClickIcon: (() -> Unit)? = null,
    fontFamily: FontFamily = _fontFamily,
    fontStyle: FontStyle = _fontStyle
) {
    HorizontalDivider()
    Spacer(Modifier.height(5.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp),
            textAlign = TextAlign.Start,
            text = text1,
            fontWeight = FontWeight.Bold,
            fontStyle = fontStyle,
            fontFamily = fontFamily
        )
        Text(
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            fontStyle = fontStyle,
            fontFamily = fontFamily,
            text = text2
        )
        if (icon != null) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(onClick = { onClickIcon?.invoke() })
            )

        }
    }
    Spacer(Modifier.height(10.dp))
}