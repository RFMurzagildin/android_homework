package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.webbyskytodolist.R

@Composable
fun AnalyticsScreen(viewModel: AnalyticsViewModel = hiltViewModel()) {
    val taskCounts by viewModel.taskCounts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Task statistics",
            color = Color.White,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                fontSize = 24.sp,
            ),
        )

        if (taskCounts.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.size(32.dp))
        } else {
            TaskGraph(taskCounts = taskCounts)
        }
    }
}