package com.app.webbyskytodolist.domain.usecases

import com.app.webbyskytodolist.data.models.ApiResponseCountTasks
import com.app.webbyskytodolist.domain.repositories.TaskRepository
import com.app.webbyskytodolist.utils.NetworkResult
import javax.inject.Inject

class GetCountTasksByUserAndDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend fun invoke(
        token: String,
        date: String,
    ): NetworkResult<ApiResponseCountTasks> {
        return taskRepository.getCountTasksByUserAndDate(token = token, date = date)
    }
}