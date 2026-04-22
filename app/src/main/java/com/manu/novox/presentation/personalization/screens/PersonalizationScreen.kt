package com.manu.novox.presentation.personalization.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manu.novox.presentation.personalization.PersonalizationEffects
import com.manu.novox.presentation.personalization.PersonalizationEvents
import com.manu.novox.presentation.personalization.PersonalizationViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizationScreen(
    navigateToSettings:()-> Unit,
    viewModel : PersonalizationViewmodel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadSettings()
    }

    LaunchedEffect(true) {
        viewModel.effect.collect { effects ->
            when(effects) {
                PersonalizationEffects.NavigateToSettings -> {navigateToSettings()}
                is PersonalizationEffects.ShowToast -> {
                    Toast.makeText(context,effects.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF3EDF7),
                        contentColor = Color(0xFF6750A4)
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = {onEvent(PersonalizationEvents.Close)}
                ) {
                    Text("Close")
                }
                Spacer(Modifier.width(20.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {onEvent(PersonalizationEvents.Save)}
                ) {
                    Text("Save")
                }
            }
        },
        topBar = {

            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9F6F0)),
                title = {
                    Text(
                        text = "Personalizations",
                        color = Color(0xFF1C1B1F),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                }
            )
        }
    ) {
        PersonalizationScreen(
            modifier = Modifier.padding(it),
            state = state.value,
            onEvent = onEvent
        )
    }
}