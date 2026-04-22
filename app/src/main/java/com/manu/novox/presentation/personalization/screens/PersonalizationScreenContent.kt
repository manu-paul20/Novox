package com.manu.novox.presentation.personalization.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manu.novox.core.utils.NovoxFontFamily
import com.manu.novox.core.utils.NovoxFontStyle
import com.manu.novox.presentation.personalization.PersonalizationEvents
import com.manu.novox.presentation.personalization.PersonalizationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizationScreen(
    modifier: Modifier,
    state: PersonalizationState,
    onEvent: (PersonalizationEvents) -> Unit
) {


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF9F6F0))
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // app font size
        ListItem(
            colors = ListItemDefaults.colors(containerColor = Color(0xFFF9F6F0)),
            headlineContent = { Text("App font size") },
            supportingContent = {
                FontSlider(
                    value = state.settings.appFontSize.toFloat(),
                    onValueChange = {
                        onEvent(PersonalizationEvents.OnAppFontSizeChange(it.toInt()))
                    }
                )
            }
        )
        Spacer(Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(Modifier.height(10.dp))

        // chat font size
        ListItem(
            colors = ListItemDefaults.colors(containerColor = Color(0xFFF9F6F0)),
            headlineContent = { Text("Chat font size") },
            supportingContent = {
                FontSlider(
                    value = state.settings.textFontSize.toFloat(),
                    onValueChange = {
                        onEvent(PersonalizationEvents.OnTextFontSizeChange(it.toInt()))
                    }
                )
            }
        )
        Spacer(Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(Modifier.height(10.dp))
        // font family drop down
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Font Family",
                fontSize = 16.sp
            )
            Spacer(Modifier.width(10.dp))
            ExposedDropdownMenuBox(
                expanded = state.isFontFamilyDropDownOpen,
                onExpandedChange = { onEvent(PersonalizationEvents.OnFontFamilyDropDownChange(it)) },
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFF3EDF7),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .border(1.dp, Color(0xFF79747E), shape = RoundedCornerShape(10.dp))
                            .menuAnchor(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.settings.fontFamily.name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(Icons.Default.ArrowDropDown, "")
                    }
                    ExposedDropdownMenu(
                        expanded = state.isFontFamilyDropDownOpen,
                        onDismissRequest = {
                            onEvent(
                                PersonalizationEvents.OnFontFamilyDropDownChange(
                                    false
                                )
                            )
                        }
                    ) {
                        NovoxFontFamily.entries.forEach {
                            DropdownMenuItem(
                                text = { Text(it.name) },
                                onClick = { onEvent(PersonalizationEvents.OnFontFamilyChange(it)) }
                            )
                        }
                    }

                }
            )
        }
        Spacer(Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(Modifier.height(10.dp))

        //font style buttons
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
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = Color(0xFFE8DEF8),
                                inactiveContainerColor = Color.Transparent,
                                inactiveContentColor = Color(0xFF49454F)
                            ),
                            selected = (state.settings.fontStyle == style),
                            onClick = { onEvent(PersonalizationEvents.OnFontStyleChange(style)) },
                            label = { Text(style.name) },
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = NovoxFontStyle.entries.size
                            )
                        )
                    }
                }
            )
        }
        Spacer(Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(Modifier.height(10.dp))

        ColorPicker(
            color = state.settings.textColor,
            text = "Text Color",
            expanded = state.isTextColorDropDownOpen,
            onSelect = {
                onEvent(PersonalizationEvents.OnTextColorChange(it))
            },
            onExpandChange = {
                onEvent(PersonalizationEvents.OnTextColorDropDown(it))
            }
        )
        Spacer(Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(Modifier.height(10.dp))
        ColorPicker(
            color = state.settings.messageBoxColor,
            text = "My message box color",
            expanded = state.isMyMessageBoxColorDropDownOpen,
            onSelect = {
                onEvent(PersonalizationEvents.OnMessageBoxColorChange(it))
            },
            onExpandChange = {
                onEvent(PersonalizationEvents.OnMessageBoxColorDropDown(it))
            }
        )
        Spacer(Modifier.height(10.dp))
        HorizontalDivider()
        Spacer(Modifier.height(10.dp))
        ColorPicker(
            color = state.settings.friendMessageBoxColor,
            text = "friends message box Color",
            expanded = state.isFriendsMessageBoxColorDropDownOpen,
            onSelect = {
                onEvent(PersonalizationEvents.OnFriendMessageBoxColorChange(it))
            },
            onExpandChange = {
                onEvent(PersonalizationEvents.OnFriendMessageBoxColorDropDown(it))
            }
        )


    }
}