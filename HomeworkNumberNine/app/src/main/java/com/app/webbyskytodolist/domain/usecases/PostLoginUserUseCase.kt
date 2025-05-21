package com.app.webbyskytodolist.domain.usecases

import com.app.webbyskytodolist.data.models.ApiBaseResponse
import com.app.webbyskytodolist.data.models.ApiRequestLoginUser
import com.app.webbyskytodolist.domain.repositories.UserRepository
import com.app.webbyskytodolist.utils.NetworkResult
import javax.inject.Inject

class PostLoginUserUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend fun invoke(
        body: ApiRequestLoginUser
    ): NetworkResult<ApiBaseResponse> =
        userRepository.postLoginUser(body = body)
}