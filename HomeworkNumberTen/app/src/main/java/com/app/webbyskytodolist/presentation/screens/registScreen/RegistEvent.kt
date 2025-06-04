package com.app.webbyskytodolist.presentation.screens.registScreen

sealed class RegistEvent {
    data class Success(val message: String): RegistEvent()
    data class Error(val message: String): RegistEvent()
    data object Loading : RegistEvent()
    data object None : RegistEvent()
}