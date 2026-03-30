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
import com.manu.novox.presentation.accountCreation.screen.AccountCreationScreen
import com.manu.novox.presentation.auth.screen.AuthScreen
import com.manu.novox.presentation.chatlist.screen.ChatListScreen

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
            composable<Routes.AuthScreen>{
                AuthScreen(
                    onNavigateToChatList = { navController.navigate(Routes.ChatListScreen) },
                    onNavigateToAccountCreation = { navController.navigate(Routes.AccountCreationScreen) }
                )
            }

            composable<Routes.AccountCreationScreen> {
                AccountCreationScreen(
                    onNavigateToChatList = {navController.navigate(Routes.ChatListScreen)}
                )
            }

            composable<Routes.ChatListScreen> {
                ChatListScreen(
                    onNavigateToAccountCreation = {navController.navigate(Routes.AccountCreationScreen)}
                )
            }
        }
    }
}