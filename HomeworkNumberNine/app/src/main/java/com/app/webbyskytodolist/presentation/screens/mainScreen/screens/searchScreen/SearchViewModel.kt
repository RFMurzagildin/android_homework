package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.searchScreen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.webbyskytodolist.data.models.ApiRequestTask
import com.app.webbyskytodolist.data.models.ApiTaskResponse
import com.app.webbyskytodolist.domain.usecases.GetSavedTokenUseCase
import com.app.webbyskytodolist.domain.usecases.GetTaskDetailsUseCase
import com.app.webbyskytodolist.domain.usecases.GetTasksByUserAndDateUseCase
import com.app.webbyskytodolist.domain.usecases.PostDeleteTaskUseCase
import com.app.webbyskytodolist.domain.usecases.PostNewTaskUseCase
import com.app.webbyskytodolist.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getTaskDetailsUseCase: GetTaskDetailsUseCase,
    private val postNewTaskUseCase: PostNewTaskUseCase,
    private val getTasksByUserAndDateUseCase: GetTasksByUserAndDateUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val postDeleteTaskUseCase: PostDeleteTaskUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<ApiTaskResponse>>(emptyList())
    val tasks: StateFlow<List<ApiTaskResponse>> get() = _tasks

    private val _taskDetails = MutableStateFlow<ApiTaskResponse?>(null)
    val taskDetails: StateFlow<ApiTaskResponse?> get() = _taskDetails

    private val _selectedDate = MutableStateFlow(LocalDate.now().toString())
    val selectedDate: StateFlow<String> get() = _selectedDate

    fun updateSelectedDate(newDate: String) {
        _selectedDate.value = newDate
        loadUserTasksByDate(date = newDate)
    }

    init{
        loadUserTasksByDate(date = LocalDate.now().toString())
    }

    fun newTask(
        description: String,
        time: String,
        selectedDate: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            val requestTask = ApiRequestTask(description, time = "${selectedDate}T${time}")
            val token = getSavedTokenUseCase.invoke()
            if(token != null){
                when(val result = postNewTaskUseCase.invoke(
                    token,
                    requestTask
                )){
                    is NetworkResult.Success -> {
                        loadUserTasksByDate(date = selectedDate)
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

    fun deleteTask(taskId: Long, date: String) {
        viewModelScope.launch(Dispatchers.IO){
            val token = getSavedTokenUseCase.invoke()
            if(token != null){
                when(val result = postDeleteTaskUseCase.invoke(token, taskId)){
                    is NetworkResult.Success -> {
                        showToast("The task deleted")
                        loadUserTasksByDate(date)
                    }
                    is NetworkResult.Error -> {
                        showToast("Error when deleting an issue: ${result.message}")
                    }
                }
            }
        }
    }

    private fun loadUserTasksByDate(
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