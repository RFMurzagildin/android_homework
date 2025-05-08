package com.app.webbyskytodolist.data.repositories

import com.app.webbyskytodolist.data.models.ApiBaseResponse
import com.app.webbyskytodolist.data.models.ApiRequestTask
import com.app.webbyskytodolist.data.models.ApiTaskResponse
import com.app.webbyskytodolist.data.remote.RemoteDataSource
import com.app.webbyskytodolist.domain.repositories.TaskRepository
import com.app.webbyskytodolist.utils.BaseApiResponse
import com.app.webbyskytodolist.utils.NetworkResult
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : BaseApiResponse(), TaskRepository {

    override suspend fun newTask(
        token: String,
        body: ApiRequestTask,
    ): NetworkResult<ApiBaseResponse> {
        return safeApiCall { remoteDataSource.newTask(token = token, body = body) }
    }

    override suspend fun getUserTasks(
        token: String,
    )
            : NetworkResult<List<ApiTaskResponse>> {
        return safeApiCall { remoteDataSource.getUserTasks(token = token) }
    }

    override suspend fun getTasksByUserAndDate(
        token: String,
        date: String,
    ): NetworkResult<List<ApiTaskResponse>> {
        return safeApiCall { remoteDataSource.getTasksByUserAndDate(token = token, date = date) }
    }

    override suspend fun getTaskDetails(
        token: String,
        taskId: Long,
    ): NetworkResult<ApiTaskResponse> {
        return safeApiCall { remoteDataSource.getTaskDetails(token, taskId) }
    }
}