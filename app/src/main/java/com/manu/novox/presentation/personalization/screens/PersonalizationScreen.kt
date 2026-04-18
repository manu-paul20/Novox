package com.manu.novox.presentation.personalization.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manu.novox.core.utils.NovoxFontFamily
import com.manu.novox.core.utils.NovoxFontStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PersonalizationScreen() {
    val isDropDownOpen  = remember { mutableStateOf(false) }
    val selected  = remember { mutableStateOf("Italic") }
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) {
                    Text("Close")
                }
                Spacer(Modifier.width(20.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {}
                ) {
                    Text("Save")
                }
            }
        },
        topBar = {

            Text(
                text = "Personalizations",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ListItem(
                headlineContent = {Text("App font size")},
                supportingContent = {
                    FontSlider(
                        value = 14f,
                        onValueChange = {}
                    )
                }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = {Text("Chat font size")},
                supportingContent = {
                    FontSlider(
                        value = 14f,
                        onValueChange = {}
                    )
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Font Family",
                    fontSize = 16.sp
                    )
                Spacer(Modifier.width(10.dp))
                ExposedDropdownMenuBox(
                    expanded = isDropDownOpen.value,
                    onExpandedChange = {isDropDownOpen.value = it},
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable{isDropDownOpen.value = true}
                                .background(color = Color.Gray, shape = RoundedCornerShape(10.dp))
                                .menuAnchor(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selected.value.ifBlank { "Select" },
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(Icons.Default.ArrowDropDown,"")
                        }
                        ExposedDropdownMenu(
                            expanded = isDropDownOpen.value,
                            onDismissRequest = {isDropDownOpen.value = false}
                        ) {
                            NovoxFontFamily.entries.forEach {
                                DropdownMenuItem(
                                    text = {it.name},
                                    onClick = {selected.value = it.name}
                                )
                            }
                        }

                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Font style",
                    fontSize = 16.sp
                    )
                Spacer(Modifier.width(10.dp))
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        NovoxFontStyle.entries.forEachIndexed { index, style ->
                            SegmentedButton(
                                selected = (style.name == selected.value),
                                onClick = {selected.value = style.name},
                                label = {Text(style.name)},
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = NovoxFontStyle.entries.size
                                )
                            )
                        }
                    }
                )
            }


        }
    }
}