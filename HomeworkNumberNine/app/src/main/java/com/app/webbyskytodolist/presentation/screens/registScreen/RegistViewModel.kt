package com.app.webbyskytodolist.presentation.screens.registScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.webbyskytodolist.data.models.ApiRequestRegistUser
import com.app.webbyskytodolist.domain.usecases.PostRegistUserUseCase
import com.app.webbyskytodolist.domain.usecases.ValidateCredentialsUseCase
import com.app.webbyskytodolist.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistViewModel @Inject constructor(
    private val postUserUseCase: PostRegistUserUseCase,
    private val validateCredentialsUseCase: ValidateCredentialsUseCase,
) : ViewModel() {

    private val _regisEvent = MutableStateFlow<RegistEvent>(RegistEvent.None)
    val registEvent = _regisEvent.asStateFlow()

    fun postUsers(
        username: String,
        password: String,
        confirmPassword: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _regisEvent.emit(RegistEvent.Loading)

            val result = postUserUseCase.invoke(
                ApiRequestRegistUser(
                    username = username,
                    password = password,
                    confirmPassword = confirmPassword
                )
            )

            when (result) {
                is NetworkResult.Success -> {
                    _regisEvent.emit(RegistEvent.Success("${result.data?.message}"))
                }

                is NetworkResult.Error -> {
                    _regisEvent.emit(RegistEvent.Error("${result.message}"))
                }
            }
        }
    }

    fun validateLogin(login: String): Boolean {
        return validateCredentialsUseCase.isValidUsername(login)
    }

    fun validatePassword(password: String): Boolean {
        return validateCredentialsUseCase.isValidPassword(password)
    }

    fun passwordsMatch(password1: String, password2: String): Boolean {
        return validateCredentialsUseCase.passwordsMatch(password1, password2)
    }

    fun clearEvent() {
        _regisEvent.tryEmit(RegistEvent.None)
    }
}