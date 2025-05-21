package com.app.webbyskytodolist.domain.usecases

import com.app.webbyskytodolist.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend fun invoke() = authRepository.logoutUser()
}