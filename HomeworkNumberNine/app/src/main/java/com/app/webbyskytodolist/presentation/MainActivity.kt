package com.app.webbyskytodolist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.webbyskytodolist.domain.usecases.GetSavedTokenUseCase
import com.app.webbyskytodolist.domain.usecases.LogoutUserUseCase
import com.app.webbyskytodolist.domain.usecases.PostValidateTokenUseCase
import com.app.webbyskytodolist.presentation.screens.LoadingScreen
import com.app.webbyskytodolist.presentation.screens.loginScreen.LoginScreen
import com.app.webbyskytodolist.presentation.screens.mainScreen.MainScreen
import com.app.webbyskytodolist.presentation.screens.registScreen.RegistrationScreen
import com.app.webbyskytodolist.presentation.ui.theme.WebbySkyToDoListTheme
import com.app.webbyskytodolist.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var validateTokenUseCase: PostValidateTokenUseCase

    @Inject
    lateinit var savedTokenUseCase: GetSavedTokenUseCase

    @Inject
    lateinit var logoutUserUseCase: LogoutUserUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WebbySkyToDoListTheme {
                val navController = rememberNavController()
                val isLoading = remember { mutableStateOf(true) }
                val isAuthenticated = remember { mutableStateOf(false) }


                LaunchedEffect(Unit) {
                    val token = savedTokenUseCase.invoke()
                    if (token != null) {
                        when (validateTokenUseCase.invoke(token)) {
                            is NetworkResult.Success -> {
                                isAuthenticated.value = true
                            }

                            is NetworkResult.Error -> {
                                logoutUserUseCase.invoke()
                                isAuthenticated.value = false
                            }
                        }
                    } else {
                        isAuthenticated.value = false
                    }
                    isLoading.value = false
                }

                val startDestination = if (isLoading.value) {
                    "loading_screen"
                } else if (isAuthenticated.value) {
                    "main_screen"
                } else {
                    "login_screen"
                }


                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable("login_screen") {
                        LoginScreen(
                            onNavigate = { route ->
                                navController.navigate(route) {
                                    popUpTo("login_screen") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("registration_screen") {
                        RegistrationScreen(
                            onNavigate = { route ->
                                navController.navigate(route) {
                                    popUpTo("registration_screen") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("main_screen") {
                        MainScreen(mainNavController = navController)
                    }
                    composable("loading_screen") {
                        LoadingScreen()
                    }
                }
            }
        }
    }
}
