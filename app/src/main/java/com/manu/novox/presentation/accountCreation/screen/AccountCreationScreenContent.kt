package com.manu.novox.presentation.accountCreation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manu.novox.presentation.accountCreation.AccountCreationEvent
import com.manu.novox.presentation.accountCreation.AccountCreationState

@Composable
fun AccountCreationScreenContent(
    state: AccountCreationState,
    onEvent: (AccountCreationEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFFF9F6F0))
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color(0XFFF3EFE7)
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(20.dp))
                HorizontalDivider()
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        focusedLabelColor = Color.Black,
                        focusedContainerColor = Color(0XFFE4E2DB),
                        unfocusedContainerColor = Color(0XFFE4E2DB)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    value = state.name,
                    label = { Text("Name") },
                    placeholder = {Text("e.g. Manu Paul")},
                    supportingText = {Text("your real name")},
                    onValueChange = { onEvent(AccountCreationEvent.SetName(it))},
                )
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0XFFE4E2DB),
                        unfocusedContainerColor = Color(0XFFE4E2DB),
                        focusedBorderColor = Color.Transparent,
                        focusedLabelColor = Color.Black
                    ),
                    shape = RoundedCornerShape(20.dp),
                    value = state.userName,
                    label = { Text("User Name") },
                    placeholder = {Text("e.g. Manu123")},
                    supportingText = {Text("your account's name")},
                    onValueChange = {onEvent(AccountCreationEvent.SetUserName(it)) },
                )
                Spacer(Modifier.height(10.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0XFF6D5E50)
                    ),
                    onClick = {
                        onEvent(AccountCreationEvent.CreateAccount)
                    }
                ) {
                    Text(
                        text = "Create"
                    )
                }

            }
        }
    }
}