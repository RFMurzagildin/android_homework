package com.app.webbyskytodolist.presentation.screens.registScreen

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
import androidx.compose.ui.text.input.KeyboardType
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
fun RegistrationScreen(
    onNavigate: (String) -> Unit,
    registViewModel: RegistViewModel = hiltViewModel(),
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    HandleRegistEvents(registViewModel, onNavigate)

    Column(
        modifier = Modifier
            .background(MainDarkColor)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create an Account",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                color = Color.White
            ),
            modifier = Modifier.padding(bottom = 54.dp)
        )

        CustomTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = null
            },
            placeholderText = "Username",
            keyboardType = KeyboardType.Email,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 4.dp),
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
                .padding(bottom = 4.dp),
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


        CustomPasswordField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = null
            },
            placeholderText = "Password again",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(bottom = 8.dp),
            shape = RoundedCornerShape(10.dp)
        )

        if (confirmPasswordError != null) {
            Text(
                text = confirmPasswordError!!,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 32.dp, bottom = 8.dp)
            )
        }


        CustomButton(
            text = "Create account"
        ) {
            val isUsernameValid = registViewModel.validateLogin(username)
            val isPasswordValid = registViewModel.validatePassword(password)
            val arePasswordsMatch = registViewModel.passwordsMatch(password, confirmPassword)

            usernameError = if (!isUsernameValid) "Invalid username" else null
            passwordError = if (!isPasswordValid) "Invalid password" else null
            confirmPasswordError = if (!arePasswordsMatch) "Passwords do not match" else null

            if (isUsernameValid && isPasswordValid && arePasswordsMatch) {
                registViewModel.postUsers(username, password, confirmPassword)
                username = ""
                password = ""
                confirmPassword = ""
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Do you have an account?",
                style = TextStyle(
                    color = TextGrayColor,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_regular))
                )
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "Log in",
                style = TextStyle(
                    color = MainButtonColor,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunito_bold))
                ),
                modifier = Modifier.clickable {
                    onNavigate("login_screen")
                }
            )
        }
    }
}

@Composable
fun HandleRegistEvents(
    registViewModel: RegistViewModel,
    onNavigate: (String) -> Unit,
) {
    val event by registViewModel.registEvent.collectAsState()
    val context = LocalContext.current
    when (event) {
        is RegistEvent.Success -> {
            Toast.makeText(context, (event as RegistEvent.Success).message, Toast.LENGTH_LONG)
                .show()
            onNavigate("login_screen")
            LaunchedEffect(Unit) {
                registViewModel.clearEvent()
            }
        }

        is RegistEvent.Error -> {
            Toast.makeText(context, (event as RegistEvent.Error).message, Toast.LENGTH_LONG).show()
            LaunchedEffect(Unit) {
                registViewModel.clearEvent()
            }
        }

        else -> {}
    }
}

