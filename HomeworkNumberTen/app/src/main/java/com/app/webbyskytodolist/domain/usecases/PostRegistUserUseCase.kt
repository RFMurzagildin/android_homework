package com.app.webbyskytodolist.domain.usecases

import com.app.webbyskytodolist.data.models.ApiBaseResponse
import com.app.webbyskytodolist.data.models.ApiRequestRegistUser
import com.app.webbyskytodolist.domain.repositories.UserRepository
import com.app.webbyskytodolist.utils.NetworkResult
import javax.inject.Inject

class PostRegistUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun invoke(
        body: ApiRequestRegistUser,
    ): NetworkResult<ApiBaseResponse> =
        userRepository.postRegisterUser(body = body)
}