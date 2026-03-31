
package com.manu.novox.presentation.chatscreen.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ChatScreen() {
    val userName = "Manu Paul"
    val profilePhoto = "https://lh3.googleusercontent.com/a/ACg8ocIrLt-cToT2YwEVfoR6fRsODj_4sN7NI-d6dGUXJ7bxl0MUrw=s96-c"
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = profilePhoto,
                    contentDescription = "user profile photo"
                )
            }
        }
    ) {innerPadding->

    }
}
