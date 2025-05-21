package com.app.webbyskytodolist.presentation.bottom_nav

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.app.webbyskytodolist.presentation.ui.theme.MainButtonColor
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor

@Composable
fun BottomNav(
    navController: NavController,
) {
    val listItems = listOf(
        BottomItem.TodayScreen,
        BottomItem.SearchScreen,
        BottomItem.AnalyticsScreen,
        BottomItem.SettingsScreen
    )

    BottomNavigation(
        backgroundColor = MainButtonColor
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRout = backStackEntry?.destination?.route
        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRout == item.route,
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = item.iconId
                        ),
                        contentDescription = item.title
                    )
                },
                selectedContentColor = MainDarkColor,
                unselectedContentColor = Color.Gray
            )
        }
    }
}