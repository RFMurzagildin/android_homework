package com.app.webbyskytodolist.presentation.screens.loginScreen

sealed class AuthEvent {
    data object Success : AuthEvent()
    data class Error(val message: String) : AuthEvent()
    data object Loading : AuthEvent()
    data object None : AuthEvent()
}