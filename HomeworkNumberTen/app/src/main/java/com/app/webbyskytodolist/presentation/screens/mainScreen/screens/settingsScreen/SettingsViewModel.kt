package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.settingsScreen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.webbyskytodolist.data.models.ApiTaskResponse
import com.app.webbyskytodolist.domain.usecases.GetSavedTokenUseCase
import com.app.webbyskytodolist.domain.usecases.GetUserTasksUseCase
import com.app.webbyskytodolist.domain.usecases.LogoutUserUseCase
import com.app.webbyskytodolist.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val logoutUserUseCase: LogoutUserUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val getUserTasksUseCase: GetUserTasksUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<ApiTaskResponse>>(emptyList())
    val tasks: StateFlow<List<ApiTaskResponse>> get() = _tasks

    fun logout() {
        viewModelScope.launch {
            logoutUserUseCase.invoke()
        }
    }

    fun exportTasksToFile() {

        viewModelScope.launch(Dispatchers.IO) {
            val token = getSavedTokenUseCase.invoke()
            if (token != null) {
                when (val result = getUserTasksUseCase.invoke(token)) {
                    is NetworkResult.Success -> {
                        val tasks = result.data ?: return@launch

                        val json = Json.encodeToString(
                            serializer = ListSerializer(ApiTaskResponse.serializer()),
                            value = tasks
                        )

                        val downloadsDir = context.getExternalFilesDir(null)?.let {
                            android.os.Environment.getExternalStoragePublicDirectory(
                                android.os.Environment.DIRECTORY_DOWNLOADS
                            )
                        }

                        if (downloadsDir != null && downloadsDir.exists()) {
                            val file = File(downloadsDir, "tasks_export_${System.currentTimeMillis()}.json")

                            try {
                                file.writeText(json)
                                showToast("Файл сохранён: ${file.absolutePath}")
                            } catch (e: Exception) {
                                showToast("Ошибка сохранения: ${e.message}")
                            }
                        } else {
                            showToast("Не удалось получить доступ к папке загрузок")
                        }
                    }

                    is NetworkResult.Error -> {
                        showToast("Ошибка получения задач: ${result.message}")
                    }
                }
            } else {
                showToast("Токен отсутствует")
            }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}