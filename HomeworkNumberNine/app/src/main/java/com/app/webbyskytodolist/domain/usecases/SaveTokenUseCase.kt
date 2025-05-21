package com.app.webbyskytodolist.domain.usecases

import com.app.webbyskytodolist.domain.repositories.AuthRepository

class SaveTokenUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun invoke(
        token: String,
    ) {
        authRepository.saveToken(token)
    }
}