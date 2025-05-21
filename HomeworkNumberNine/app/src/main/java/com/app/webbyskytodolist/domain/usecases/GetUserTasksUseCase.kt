package com.app.webbyskytodolist.domain.usecases

import com.app.webbyskytodolist.data.models.ApiTaskResponse
import com.app.webbyskytodolist.domain.repositories.TaskRepository
import com.app.webbyskytodolist.utils.NetworkResult
import javax.inject.Inject

class GetUserTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend fun invoke(
        token: String,
    ): NetworkResult<List<ApiTaskResponse>> {
        return taskRepository.getUserTasks(token)
    }
}