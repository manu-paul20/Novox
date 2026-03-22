package com.manu.novox.core.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute

@Composable
fun BottomNavBar(
    selectedRoute: NavDestination?,
    onClickNavItem:(Routes)-> Unit
) {
    NavigationBar(
        windowInsets = WindowInsets(0.dp),
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
            .clip(RoundedCornerShape(50.dp))

    ) {
        TOP_LEVEL_DESTINATION.forEach { (route, item) ->
            val isSelected = (selectedRoute?.hasRoute(route::class)==true)
            NavigationBarItem(
                selected = isSelected,
                onClick = {onClickNavItem(route)},
                label = { Text(item.title) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                    )
                }
            )
        }
    }
}

