package com.app.webbyskytodolist.presentation.bottom_nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.webbyskytodolist.presentation.screens.mainScreen.screens.profileScreen.AnalyticsScreen
import com.app.webbyskytodolist.presentation.screens.mainScreen.screens.searchScreen.SearchScreen
import com.app.webbyskytodolist.presentation.screens.mainScreen.screens.settingsScreen.SettingsScreen
import com.app.webbyskytodolist.presentation.screens.mainScreen.screens.todayScreen.TodayScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    mainNavController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = "today_screen"
    ){
        composable("today_screen") {
            TodayScreen()
        }
        composable("search_screen") {
            SearchScreen()
        }
        composable("analytics_screen") {
            AnalyticsScreen()
        }
        composable("settings_screen") {
            SettingsScreen(navController = mainNavController)
        }
    }
}