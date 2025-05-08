package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDarkColor)
    ) {
        Row {
            Text(text = "Today")
        }
    }
}