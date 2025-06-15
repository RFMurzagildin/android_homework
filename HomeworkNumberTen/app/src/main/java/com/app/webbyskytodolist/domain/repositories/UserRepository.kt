package com.app.webbyskytodolist.domain.repositories

import com.app.webbyskytodolist.data.models.ApiBaseResponse
import com.app.webbyskytodolist.data.models.ApiRequestLoginUser
import com.app.webbyskytodolist.data.models.ApiRequestRegistUser
import com.app.webbyskytodolist.utils.NetworkResult

interface UserRepository {
    suspend fun postRegisterUser(body: ApiRequestRegistUser): NetworkResult<ApiBaseResponse>
    suspend fun postLoginUser(body: ApiRequestLoginUser): NetworkResult<ApiBaseResponse>
    suspend fun validateToken(token: String): NetworkResult<ApiBaseResponse>
}