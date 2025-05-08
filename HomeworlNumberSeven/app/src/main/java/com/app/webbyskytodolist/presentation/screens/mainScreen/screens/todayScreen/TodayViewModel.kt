package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.todayScreen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.webbyskytodolist.data.models.ApiRequestTask
import com.app.webbyskytodolist.data.models.ApiTaskResponse
import com.app.webbyskytodolist.domain.usecases.GetSavedTokenUseCase
import com.app.webbyskytodolist.domain.usecases.GetTaskDetailsUseCase
import com.app.webbyskytodolist.domain.usecases.GetTasksByUserAndDateUseCase
import com.app.webbyskytodolist.domain.usecases.PostNewTaskUseCase
import com.app.webbyskytodolist.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor (
    private val postNewTaskUseCase: PostNewTaskUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val getTasksByUserAndDateUseCase: GetTasksByUserAndDateUseCase,
    private val getTaskDetailsUseCase: GetTaskDetailsUseCase,
    @ApplicationContext private val context: Context
) : ViewModel(){

    private val _tasks = MutableStateFlow<List<ApiTaskResponse>>(emptyList())
    val tasks: StateFlow<List<ApiTaskResponse>> get() = _tasks

    private val _taskDetails = MutableStateFlow<ApiTaskResponse?>(null)
    val taskDetails: StateFlow<ApiTaskResponse?> get() = _taskDetails

    fun newTask(
        description: String,
        date: String,
        time: String
    ){
        viewModelScope.launch(Dispatchers.IO) {

            val requestTask = ApiRequestTask(description, time = "${date}T${time}")
            val token = getSavedTokenUseCase.invoke()
            if(token != null){
                when(val result = postNewTaskUseCase.invoke(
                    token,
                    requestTask
                )){
                    is NetworkResult.Success -> {
                        loadUserTasksByDate(date = getCurrentDate())
                    }

                    is NetworkResult.Error -> {
                        showToast("Failed to add task: ${result.message}")
                    }
                }
            }else{
                showToast("Authentication error: Token is null")
            }


        }
    }

    fun loadUserTasksByDate(
        date: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = getSavedTokenUseCase.invoke()
            if(token != null){
                when(val result = getTasksByUserAndDateUseCase.invoke(
                    token,
                    date
                )){
                    is NetworkResult.Success -> {
                        _tasks.value = result.data ?: emptyList()
                    }
                    is NetworkResult.Error -> {
                        showToast("Error loading tasks: ${result.message}")
                    }

                }
            }else {
                showToast("Authentication error: Token is null")
            }
        }
    }

    fun loadTaskDetails(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val token = getSavedTokenUseCase.invoke()
            if (token != null) {
                when (val result = getTaskDetailsUseCase.invoke(token, taskId)) {
                    is NetworkResult.Success -> {
                        _taskDetails.value = result.data
                    }
                    is NetworkResult.Error -> {
                        showToast("Error loading task details: ${result.message}")
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}