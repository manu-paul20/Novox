package com.manu.novox.presentation.auth.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun MessageDialog(
    errorMessage: String,
    onClose: () -> Unit
) {
    val isPassResetAlert = errorMessage.contains("reset email")
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Dialog(
            onDismissRequest = {}
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
                        .background(if (isPassResetAlert) Color.Green else Color.Red)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = if (errorMessage.contains("reset email")) {
                            Icons.Default.Check
                        } else {
                            Icons.Default.Close
                        },
                        tint = Color.White,
                        contentDescription = null
                    )
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    text = errorMessage,
                    textAlign = TextAlign.Center
                )
                TextButton(
                    onClick = {}
                ) {
                    Text("OK")
                }
            }

        }
    }
}