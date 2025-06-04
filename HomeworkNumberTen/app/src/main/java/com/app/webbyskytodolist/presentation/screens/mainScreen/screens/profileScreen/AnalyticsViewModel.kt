package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.profileScreen

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.webbyskytodolist.domain.usecases.GetCountTasksByUserAndDateUseCase
import com.app.webbyskytodolist.domain.usecases.GetSavedTokenUseCase
import com.app.webbyskytodolist.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val getCountTasksUseCase: GetCountTasksByUserAndDateUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _taskCounts = MutableStateFlow<List<TaskCountData>>(emptyList())
    val taskCounts: StateFlow<List<TaskCountData>> get() = _taskCounts

    init {
        fetchTaskCounts()
    }

    private fun fetchTaskCounts() {
        viewModelScope.launch {
            val today = LocalDate.now()
            val daysAhead = 7
            val token = getSavedTokenUseCase.invoke()

            val taskCounts = mutableListOf<TaskCountData>()
            if(token != null){
                for(i in 0 until daysAhead){
                    val currentDate = today.plusDays(i.toLong())
                    val dateStr = currentDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()).substring(0, 1)
                    when(val result = getCountTasksUseCase.invoke(
                        token,
                        currentDate.toString()
                    )){
                        is NetworkResult.Success -> {
                            taskCounts.add(TaskCountData(dateStr, result.data!!.count))
                        }
                        is NetworkResult.Error -> {
                            showToast("Error: ${result.message}")
                        }
                    }
                }
            }
            _taskCounts.value = taskCounts
        }
    }

    private fun showToast(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}