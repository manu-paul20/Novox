package com.manu.novox.presentation.personalization.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FontSlider(
    value: Float,
    onValueChange: (Float) -> Unit
) {


    Slider(
        onValueChange = onValueChange,
        steps = 9,
        colors = SliderDefaults.colors(
            activeTrackColor = Color(0xFF6750A4),
            inactiveTrackColor = Color(0xFFE8DEF8),
            activeTickColor = Color(0xFFFFFFFF),
            inactiveTickColor = Color(0xFF6750A4)
        ),
        valueRange = 10f..20f,
        thumb = {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(color = Color(0xFF6750A4), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${it.value.toInt()}",
                    color = Color.White
                )
            }
        },
        value = value
    )
}