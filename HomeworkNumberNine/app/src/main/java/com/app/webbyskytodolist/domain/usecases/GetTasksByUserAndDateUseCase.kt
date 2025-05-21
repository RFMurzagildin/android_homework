package com.app.webbyskytodolist.domain.usecases

import com.app.webbyskytodolist.data.models.ApiTaskResponse
import com.app.webbyskytodolist.domain.repositories.TaskRepository
import com.app.webbyskytodolist.utils.NetworkResult
import javax.inject.Inject

class GetTasksByUserAndDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend fun invoke(
        token: String,
        date: String,
    ): NetworkResult<List<ApiTaskResponse>> {
        return taskRepository.getTasksByUserAndDate(token = token, date = date)
    }
}