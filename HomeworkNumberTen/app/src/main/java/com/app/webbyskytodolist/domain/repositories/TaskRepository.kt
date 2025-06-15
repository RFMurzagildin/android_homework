package com.app.webbyskytodolist.domain.repositories

import com.app.webbyskytodolist.data.models.ApiBaseResponse
import com.app.webbyskytodolist.data.models.ApiRequestTask
import com.app.webbyskytodolist.data.models.ApiResponseCountTasks
import com.app.webbyskytodolist.data.models.ApiTaskResponse
import com.app.webbyskytodolist.utils.NetworkResult

interface TaskRepository {
    suspend fun newTask(
        token: String,
        body: ApiRequestTask,
    ): NetworkResult<ApiBaseResponse>

    suspend fun getUserTasks(
        token: String,
    ): NetworkResult<List<ApiTaskResponse>>

    suspend fun getTasksByUserAndDate(
        token: String,
        date: String,
    ): NetworkResult<List<ApiTaskResponse>>

    suspend fun getTaskDetails(
        token: String,
        taskId: Long,
    ): NetworkResult<ApiTaskResponse>

    suspend fun getCountTasksByUserAndDate(
        token: String,
        date: String,
    ): NetworkResult<ApiResponseCountTasks>

    suspend fun deleteTask(
        token: String,
        taskId: Long
    ): NetworkResult<ApiBaseResponse>
}