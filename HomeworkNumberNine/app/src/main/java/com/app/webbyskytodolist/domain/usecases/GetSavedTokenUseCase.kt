package com.app.webbyskytodolist.domain.usecases

import com.app.webbyskytodolist.domain.repositories.AuthRepository
import javax.inject.Inject

class GetSavedTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend fun invoke(): String? = authRepository.getSavedToken()
}