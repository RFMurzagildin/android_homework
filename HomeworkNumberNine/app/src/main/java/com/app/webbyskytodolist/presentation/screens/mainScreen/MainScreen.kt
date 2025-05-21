package com.app.webbyskytodolist.presentation.screens.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.webbyskytodolist.presentation.bottom_nav.BottomNav
import com.app.webbyskytodolist.presentation.bottom_nav.NavGraph
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor

@Composable
fun MainScreen(mainNavController: NavHostController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) {
        padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MainDarkColor)
        ) {
            NavGraph(navHostController = navController, mainNavController = mainNavController)
        }
    }
}