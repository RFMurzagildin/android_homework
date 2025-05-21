package com.app.webbyskytodolist.domain.usecases

import com.app.webbyskytodolist.data.models.ApiBaseResponse
import com.app.webbyskytodolist.data.models.ApiRequestTask
import com.app.webbyskytodolist.domain.repositories.TaskRepository
import com.app.webbyskytodolist.utils.NetworkResult
import javax.inject.Inject

class PostNewTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend fun invoke(
        token: String,
        body: ApiRequestTask,
    ): NetworkResult<ApiBaseResponse> =
        taskRepository.newTask(token = token, body = body)
}