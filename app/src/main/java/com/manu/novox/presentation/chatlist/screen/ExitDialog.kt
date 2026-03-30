package com.manu.novox.presentation.chatlist.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.manu.novox.core.utils.NovoxColors
import com.manu.novox.core.utils.toColor

@Composable
fun ExitDialog(
    onClose:()-> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = onClose
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Red)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Are you sure you want to exit?",
                    textAlign = TextAlign.Center
                )
                Row{
                    TextButton(
                        onClick = {
                            (context as? Activity)?.finish()
                        }
                    ) {
                        Text("Yes")
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = NovoxColors.Light_Blue.toColor()),

                        onClick = onClose
                    ) {
                        Text("NO")
                    }
                }
            }

        }
    }
}