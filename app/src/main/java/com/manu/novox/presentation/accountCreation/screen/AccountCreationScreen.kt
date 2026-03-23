package com.manu.novox.presentation.accountCreation.screen

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
import com.manu.novox.presentation.accountCreation.AccountCreationEffect
import com.manu.novox.presentation.accountCreation.AccountCreationEvent
import com.manu.novox.presentation.accountCreation.AccountCreationViewmodel
import com.manu.novox.presentation.auth.screen.MessageDialog

@Composable
fun AccountCreationScreen(
    viewModel: AccountCreationViewmodel = hiltViewModel(),
    onNavigateToChatList:()-> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.effect.collect { effect->
            when(effect){
                AccountCreationEffect.NavigateToChatList -> {onNavigateToChatList()}
                is AccountCreationEffect.ShowToast -> {
                    Toast.makeText(context,effect.message,Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    if(state.value.isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }
    if (state.value.error.isNotBlank()){
        MessageDialog(
            errorMessage = state.value.error,
            onClose = {onEvent(AccountCreationEvent.ResetErrorMessage)}
        )
    }

    AccountCreationScreenContent(state.value,onEvent)
}