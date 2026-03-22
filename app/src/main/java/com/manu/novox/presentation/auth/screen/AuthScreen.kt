package com.manu.novox.presentation.auth.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manu.novox.presentation.auth.AuthEffect
import com.manu.novox.presentation.auth.AuthEvent
import com.manu.novox.presentation.auth.AuthViewModel

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToAccountCreation: () -> Unit
) {

    val state = authViewModel.state.collectAsStateWithLifecycle()
    val onEvent = authViewModel::onEvent
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (state.value.isUserLoggedIn){
            onNavigateToHome()
        }
    }
    LaunchedEffect(true) {
        authViewModel.authEffect.collect { effect->
            when (effect){
                AuthEffect.NavigateToHome->{
                    onNavigateToHome()
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
    if(state.value.message.isNotBlank()){
        MessageDialog(
            errorMessage = state.value.message,
            onClose = {onEvent(AuthEvent.ResetMessageDialog)}
        )
    }
    AuthScreenContent(state.value,onEvent)
}