package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.settingsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.webbyskytodolist.R
import com.app.webbyskytodolist.presentation.customs.CustomButton
import com.app.webbyskytodolist.presentation.ui.theme.MainButtonColor
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDarkColor),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Settings",
                color = Color.White,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.nunito_bold))
            )

            Image(
                painter = painterResource(id = R.drawable.mingcute_exit_line),
                contentDescription = "Exit Icon",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        navController.navigate("login_screen") {
                            popUpTo(0)
                        }
                        settingsViewModel.logout()
                    }
            )
        }

        Text(
            text = "Theme",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .padding(bottom = 8.dp)
                .align(Alignment.Start),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontSize = 20.sp
            )
        )

        ThemeSelector()

        Text(
            text = "Exporting tasks",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .padding(bottom = 8.dp)
                .align(Alignment.Start),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.nunito_regular)),
                fontSize = 20.sp
            )
        )

        CustomButton(
            text = "Export",
            modifier = Modifier
                .size(124.dp, 40.dp)
                .padding(start = 20.dp)
                .align(Alignment.Start),
            fontFamily = FontFamily(Font(R.font.nunito_regular))
        ) { }
    }
}

@Composable
fun ThemeSelector() {
    val themes = listOf("Light", "Dark", "System")
    var selectedTheme by remember { mutableStateOf(themes[1]) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MainButtonColor),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        themes.forEach { theme ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 3.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .then(
                        if (theme == selectedTheme) {
                            Modifier
                                .background(Color.White)
                                .border(
                                    width = 2.dp,
                                    color = MainDarkColor,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        } else {
                            Modifier
                        }
                    )
                    .clickable {
                        selectedTheme = theme
                    }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = theme,
                    color = if (theme == selectedTheme) MainDarkColor else Color.White,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}