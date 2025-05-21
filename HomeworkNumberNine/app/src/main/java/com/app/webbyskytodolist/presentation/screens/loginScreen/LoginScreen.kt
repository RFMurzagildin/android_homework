package com.app.webbyskytodolist.presentation.screens.loginScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.webbyskytodolist.R
import com.app.webbyskytodolist.presentation.customs.CustomButton
import com.app.webbyskytodolist.presentation.customs.CustomPasswordField
import com.app.webbyskytodolist.presentation.customs.CustomTextField
import com.app.webbyskytodolist.presentation.ui.theme.MainButtonColor
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor
import com.app.webbyskytodolist.presentation.ui.theme.TextGrayColor

@Composable
fun LoginScreen(
    onNavigate: (String) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    HandleAuthEvents(loginViewModel, onNavigate)

    Column(
        modifier = Modifier
            .background(MainDarkColor)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login Now",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                color = Color.White
            ),
            modifier = Modifier
                .padding(bottom = 54.dp)
        )
        CustomTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = null
            },
            placeholderText = "Username",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(10.dp)
        )

        if (usernameError != null) {
            Text(
                text = usernameError!!,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 42.dp)
                    .padding(bottom = 4.dp)
                    .align(Alignment.Start),
                fontSize = 12.sp,
            )
        }

        CustomPasswordField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null
            },
            placeholderText = "Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(10.dp)
        )

        if (passwordError != null) {
            Text(
                text = passwordError!!,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 42.dp)
                    .padding(bottom = 4.dp)
                    .align(Alignment.Start),
                fontSize = 12.sp,
            )
        }

        CustomButton(
            text = "Login Now"
        ) {
            val isUsernameValid = loginViewModel.validateLogin(login = username)
            val isPasswordValid = loginViewModel.validatePassword(password = password)

            usernameError = if (!isUsernameValid) "Invalid username" else null
            passwordError = if (!isPasswordValid) "Invalid password" else null

            if (isUsernameValid && isPasswordValid) {
                loginViewModel.login(username = username, password = password)
            }
        }


        Row {
            Text(
                text = "Don't have account?",
                style = TextStyle(
                    color = TextGrayColor,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular))
                )
            )
            Spacer(modifier = Modifier.padding(1.dp))
            Text(
                text = "Create one",
                style = TextStyle(
                    color = MainButtonColor,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_bold))
                ),
                modifier = Modifier
                    .clickable {
                        onNavigate("registration_screen")
                    }
            )
        }
    }
}

@Composable
fun HandleAuthEvents(
    loginViewModel: LoginViewModel,
    onNavigate: (String) -> Unit,
) {
    val event by loginViewModel.authEvent.collectAsState()
    val context = LocalContext.current
    when (event) {
        is AuthEvent.Success -> {
            onNavigate("main_screen")
            LaunchedEffect(Unit) {
                loginViewModel.clearEvent()
            }
        }

        is AuthEvent.Error -> {
            Toast.makeText(context, (event as AuthEvent.Error).message, Toast.LENGTH_LONG).show()
            LaunchedEffect(Unit) {
                loginViewModel.clearEvent()
            }
        }

        else -> {}
    }
}

