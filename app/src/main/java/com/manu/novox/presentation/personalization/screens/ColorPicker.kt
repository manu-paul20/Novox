package com.manu.novox.presentation.personalization.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.manu.novox.core.utils.NovoxColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPicker(
    color: NovoxColors,
    text: String,
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    onSelect:(NovoxColors)-> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Font Family")
        Spacer(Modifier.width(10.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { onExpandChange(it) },
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick() }
                        .background(color = Color.Gray, shape = RoundedCornerShape(10.dp))
                        .menuAnchor(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = color.name,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Default.ArrowDropDown, "")
                }
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {onExpandChange(false)},
                    content = {
                        NovoxColors.entries.forEach {
                            DropdownMenuItem(
                                text = { Text(it.name) },
                                onClick = { onSelect(it)}
                            )
                        }
                    }
                )

            }
        )
    }
}