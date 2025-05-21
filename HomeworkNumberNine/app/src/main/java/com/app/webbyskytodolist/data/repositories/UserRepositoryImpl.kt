package com.app.webbyskytodolist.data.repositories

import com.app.webbyskytodolist.data.models.ApiBaseResponse
import com.app.webbyskytodolist.data.models.ApiRequestLoginUser
import com.app.webbyskytodolist.data.models.ApiRequestRegistUser
import com.app.webbyskytodolist.data.remote.RemoteDataSource
import com.app.webbyskytodolist.domain.repositories.UserRepository
import com.app.webbyskytodolist.utils.BaseApiResponse
import com.app.webbyskytodolist.utils.NetworkResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : BaseApiResponse(), UserRepository {
    override suspend fun postRegisterUser(body: ApiRequestRegistUser): NetworkResult<ApiBaseResponse> {
        return safeApiCall { remoteDataSource.postRegisterUser(body = body) }
    }

    override suspend fun postLoginUser(body: ApiRequestLoginUser): NetworkResult<ApiBaseResponse> {
        return safeApiCall { remoteDataSource.postLoginUser(body = body) }
    }

    override suspend fun validateToken(token: String): NetworkResult<ApiBaseResponse> {
        return safeApiCall { remoteDataSource.validateToken(token = token) }
    }
}