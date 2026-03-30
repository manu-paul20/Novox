package com.manu.novox.presentation.auth.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manu.novox.presentation.auth.AuthEffect
import com.manu.novox.presentation.auth.AuthEvent
import com.manu.novox.presentation.auth.AuthViewModel

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToChatList: () -> Unit,
    onNavigateToAccountCreation: () -> Unit
) {

    val state = authViewModel.state.collectAsStateWithLifecycle()
    val onEvent = authViewModel::onEvent
    val context = LocalContext.current

    LaunchedEffect(state.value.isUserLoggedIn) {
        if (state.value.isUserLoggedIn){
            onNavigateToChatList()
        }
    }
    LaunchedEffect(true) {
        authViewModel.authEffect.collect { effect->
            when (effect){
                AuthEffect.NavigateToHome->{
                    onNavigateToChatList()
                }

                is AuthEffect.ShowToast -> {
                    Toast.makeText(context,effect.message, Toast.LENGTH_LONG).show()
                }

                AuthEffect.NavigateToAccountCreation -> {
                    onNavigateToAccountCreation()
                }
            }
        }
    }
    AuthScreenContent(state.value,onEvent)
    if(state.value.message.isNotBlank()){
        MessageDialog(
            errorMessage = state.value.message,
            onClose = {onEvent(AuthEvent.ResetMessageDialog)}
        )
    }
    if (state.value.isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }
}