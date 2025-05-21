package com.app.webbyskytodolist.data.remote

import com.app.webbyskytodolist.data.models.ApiBaseResponse
import com.app.webbyskytodolist.data.models.ApiRequestLoginUser
import com.app.webbyskytodolist.data.models.ApiRequestRegistUser
import com.app.webbyskytodolist.data.models.ApiRequestTask
import com.app.webbyskytodolist.data.models.ApiResponseCountTasks
import com.app.webbyskytodolist.data.models.ApiTaskResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun postRegisterUser(
        body: ApiRequestRegistUser,
    ): Response<ApiBaseResponse> = apiService.postRegisterUser(body = body)

    suspend fun postLoginUser(
        body: ApiRequestLoginUser,
    ): Response<ApiBaseResponse> = apiService.postLoginUser(body = body)

    suspend fun validateToken(
        token: String,
    ): Response<ApiBaseResponse> = apiService.validateToken("Bearer $token")

    suspend fun newTask(
        token: String,
        body: ApiRequestTask,
    ): Response<ApiBaseResponse> = apiService.newTask("Bearer $token", body = body)

    suspend fun getUserTasks(
        token: String,
    ): Response<List<ApiTaskResponse>> = apiService.getUserTasks("Bearer $token")

    suspend fun getTasksByUserAndDate(
        token: String,
        date: String,
    ): Response<List<ApiTaskResponse>> =
        apiService.getTasksByUserAndDate("Bearer $token", date = date)

    suspend fun getTaskDetails(
        token: String,
        taskId: Long,
    ): Response<ApiTaskResponse> {
        return apiService.getTaskDetails("Bearer $token", taskId)
    }

    suspend fun getCountTasksByUserAndDate(
        token: String,
        date: String,
    ): Response<ApiResponseCountTasks> = apiService.getCountTasksByUserAndDate("Bearer $token", date = date)

    suspend fun deleteTask(
        token: String,
        taskId: Long,
    ): Response<ApiBaseResponse> {
        return apiService.deleteTask("Bearer $token", taskId)
    }
}