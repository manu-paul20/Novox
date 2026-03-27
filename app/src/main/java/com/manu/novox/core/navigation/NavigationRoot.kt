package com.manu.novox.core.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.manu.novox.presentation.auth.screen.AuthScreen

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        bottomBar = {
            when {
                currentDestination?.hasRoute<Routes.ChatListScreen>() == true ||
                        currentDestination?.hasRoute<Routes.SettingsScreen>() == true -> {
                    BottomNavBar(
                        selectedRoute = currentDestination,
                        onClickNavItem = {
                            navController.navigate(it) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = false
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { -it } },
            navController = navController,
            startDestination = Routes.AuthScreen
        ) {
            composable<Routes.ChatListScreen>{

            }
            composable<Routes.AuthScreen>{
                AuthScreen(
                    onNavigateToChatList = { navController.navigate(Routes.ChatScreen) },
                    onNavigateToAccountCreation = { navController.navigate(Routes.AccountCreationScreen) }
                )
            }
        }
    }
}