package com.app.webbyskytodolist.presentation.bottom_nav

import com.app.webbyskytodolist.R

sealed class BottomItem(
    val title: String,
    val iconId: Int,
    val route: String,
) {
    data object TodayScreen :
        BottomItem("TodayScreen", R.drawable.baseline_today_24, "today_screen")

    data object SearchScreen :
        BottomItem("SearchScreen", R.drawable.baseline_calendar_month_24, "search_screen")

    data object ProfileScreen :
        BottomItem("ProfileScreen", R.drawable.baseline_person_24, "profile_screen")

    data object SettingsScreen :
        BottomItem("SettingsScreen", R.drawable.baseline_settings_24, "settings_screen")
}