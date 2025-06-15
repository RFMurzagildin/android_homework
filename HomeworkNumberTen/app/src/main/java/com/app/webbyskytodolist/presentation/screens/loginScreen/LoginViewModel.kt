package com.app.webbyskytodolist.presentation.screens.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.webbyskytodolist.data.models.ApiRequestLoginUser
import com.app.webbyskytodolist.domain.usecases.PostLoginUserUseCase
import com.app.webbyskytodolist.domain.usecases.SaveTokenUseCase
import com.app.webbyskytodolist.domain.usecases.ValidateCredentialsUseCase
import com.app.webbyskytodolist.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUserUseCase: PostLoginUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val validateCredentialsUseCase: ValidateCredentialsUseCase,
) : ViewModel() {

    private val _authEvent = MutableStateFlow<AuthEvent>(AuthEvent.None)
    val authEvent = _authEvent.asStateFlow()

    fun login(
        username: String,
        password: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _authEvent.emit(AuthEvent.Loading)

            val result = postLoginUserUseCase.invoke(
                ApiRequestLoginUser(
                    username = username,
                    password = password
                )
            )

            when (result) {
                is NetworkResult.Success -> {
                    val token: String = result.data?.message.toString()
                    saveToken(token)
                    _authEvent.emit(AuthEvent.Success)
                }

                is NetworkResult.Error -> {
                    _authEvent.emit(AuthEvent.Error("${result.message}"))
                }
            }
        }
    }

    private fun saveToken(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            saveTokenUseCase.invoke(token)
        }
    }

    fun validateLogin(login: String): Boolean {
        return validateCredentialsUseCase.isValidUsername(login)
    }

    fun validatePassword(password: String): Boolean {
        return validateCredentialsUseCase.isValidPassword(password)
    }


    fun clearEvent() {
        _authEvent.tryEmit(AuthEvent.None)
    }
}