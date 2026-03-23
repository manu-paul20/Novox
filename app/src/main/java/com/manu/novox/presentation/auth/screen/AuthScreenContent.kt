package com.manu.novox.presentation.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manu.novox.R
import com.manu.novox.presentation.auth.AuthEvent
import com.manu.novox.presentation.auth.AuthState

@Composable

fun AuthScreenContent(
    state: AuthState,
    onEvent:(AuthEvent)-> Unit
) {
    val context = LocalContext.current
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
                    text = if(state.isSignInMode) "Sign In" else "Sign Up",
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.height(40.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0XFFE4E2DB),
                        unfocusedContainerColor = Color(0XFFE4E2DB)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    value = state.email,
                    label = {Text("Email")},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "user email"
                        )
                    },
                    onValueChange = { onEvent(AuthEvent.SetEmail(it)) },
                )
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0XFFE4E2DB),
                        unfocusedContainerColor = Color(0XFFE4E2DB)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    value = state.pass,
                    label = {Text("Password")},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Password,
                            contentDescription = "user email"
                        )
                    },
                    onValueChange = { onEvent(AuthEvent.SetPassword(it)) },
                )
                Spacer(Modifier.height(10.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0XFF6D5E50)
                    ),
                    onClick = {
                        if (state.isSignInMode) {
                            onEvent(AuthEvent.SignInWithEmailPass)
                        } else {
                            onEvent(AuthEvent.SignUpWithEmailPass)
                        }
                    }
                ) {
                    Text(
                        text = if(state.isSignInMode) "Sign In" else "Sign Up"
                    )
                }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (state.isSignInMode) {
                            TextButton(
                                onClick = {}
                            ) {
                                Text(
                                    text = "Forgot Password ?",
                                    textDecoration = TextDecoration.Underline,
                                    color = Color(0XFF6D5E50)
                                )
                            }
                        }

                        TextButton(
                            onClick = {
                                if (state.isSignInMode) {
                                    onEvent(AuthEvent.SetModeToSignUp)
                                } else {
                                    onEvent(AuthEvent.SetModeToSignIn)
                                }
                            }
                        ) {
                            Text(
                                text = if (state.isSignInMode) "Sign Up" else "Sign In",
                                color = Color(0XFF6D5E50)
                            )
                        }
                    }

                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "OR"
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }
                OutlinedButton(
                    onClick = { onEvent(AuthEvent.SignInWithGoogle(context)) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(R.drawable.googleicon),
                            contentDescription = "google icon"
                        )
                        Spacer(Modifier.width(10.dp))
                        Text("Continue with Google")
                    }
                }

            }
        }
    }
}