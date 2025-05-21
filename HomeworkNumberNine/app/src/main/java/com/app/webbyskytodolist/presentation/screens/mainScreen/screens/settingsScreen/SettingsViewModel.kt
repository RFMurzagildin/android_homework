package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.webbyskytodolist.domain.usecases.LogoutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUserUseCase: LogoutUserUseCase,
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            logoutUserUseCase.invoke()
        }
    }
}