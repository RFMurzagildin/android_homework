package com.app.webbyskytodolist.presentation.screens.mainScreen.screens.searchScreen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.webbyskytodolist.R
import com.app.webbyskytodolist.data.models.ApiTaskResponse
import com.app.webbyskytodolist.presentation.customs.CustomButton
import com.app.webbyskytodolist.presentation.customs.CustomFloatingButton
import com.app.webbyskytodolist.presentation.customs.CustomTextField
import com.app.webbyskytodolist.presentation.screens.mainScreen.screens.todayScreen.mapToTime
import com.app.webbyskytodolist.presentation.ui.theme.MainButtonColor
import com.app.webbyskytodolist.presentation.ui.theme.MainDarkColor
import com.app.webbyskytodolist.presentation.ui.theme.ScrimButtonSheetColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val selectedDate by searchViewModel.selectedDate.collectAsState()

    var isTaskDetailsVisible by remember { mutableStateOf(false) }
    val taskDetails by searchViewModel.taskDetails.collectAsState()

    var isMenuExpanded by remember { mutableStateOf(false) }

    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var task by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("12:00") }
    val tasks by searchViewModel.tasks.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainDarkColor)
    ){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        val year = calendar.get(Calendar.YEAR)
                        val month = calendar.get(Calendar.MONTH)
                        val day = calendar.get(Calendar.DAY_OF_MONTH)

                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _, selectedYear, selectedMonth, selectedDay ->
                                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                                searchViewModel.updateSelectedDate(formattedDate)
                            },
                            year,
                            month,
                            day
                        )
                        datePickerDialog.show()
                    }
                ){
                    Icon(
                        painter = painterResource(R.drawable.baseline_calendar_month_24),
                        contentDescription = "Time",
                        tint = MainButtonColor,
                        modifier = Modifier.size(64.dp)
                    )
                }
                Text(
                    text =  selectedDate,
                    color = Color.White,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nunito_regular)),
                        fontSize = 18.sp
                    )
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.5f)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                items(
                    items = tasks,
                    key = {it.id}
                ){ taskDto ->
                    TaskItem(
                        task = taskDto,
                        onTaskClick = { taskId ->
                            searchViewModel.loadTaskDetails(taskId)
                            isTaskDetailsVisible = true
                        }
                    )
                }
            }
        }

        CustomFloatingButton(
            onClick = {
                isBottomSheetVisible = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

        if (isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { isBottomSheetVisible = false },
                containerColor = MainDarkColor,
                scrimColor = ScrimButtonSheetColor,
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.height(300.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        CustomTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp)
                            ,
                            value = task,
                            onValueChange = { task = it },
                            placeholderText = "Task",
                            shape = RoundedCornerShape(10.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 32.dp)
                            ,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            IconButton(
                                onClick = {
                                    val calendar = Calendar.getInstance()
                                    val hour = calendar.get(Calendar.HOUR_OF_DAY)
                                    val minute = calendar.get(Calendar.MINUTE)

                                    val timePickerDialog = TimePickerDialog(
                                        context,
                                        { _, selectedHour, selectedMinute ->
                                            val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                                            selectedTime = time
                                        },
                                        hour,
                                        minute,
                                        true
                                    )
                                    timePickerDialog.show()
                                }
                            ){
                                androidx.compose.material3.Icon(
                                    painter = painterResource(R.drawable.baseline_access_time_24),
                                    contentDescription = "Time",
                                    tint = MainButtonColor,
                                    modifier = Modifier.size(64.dp)
                                )
                            }

                            Text(
                                text = selectedTime,
                                color = Color.White,
                                fontSize = 32.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        CustomButton(
                            text = "Add task"
                        ) {
                            isBottomSheetVisible = false
                            println(selectedTime)
                            searchViewModel.newTask(
                                description = task,
                                time = selectedTime,
                                selectedDate = selectedDate
                            )
                            selectedTime = "12:00"
                            task = ""
                        }
                    }
                }
            }
        }
    }

    if (isTaskDetailsVisible && taskDetails != null) {
        ModalBottomSheet(
            onDismissRequest = { isTaskDetailsVisible = false },
            containerColor = MainDarkColor,
            scrimColor = ScrimButtonSheetColor,
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier.height(300.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End

                    ) {
                        IconButton(
                            onClick = { isMenuExpanded = true }
                        ) {
                            androidx.compose.material3.Icon(
                                painter = painterResource(R.drawable.baseline_more_horiz_24),
                                contentDescription = "More",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = isMenuExpanded,
                            onDismissRequest = { isMenuExpanded = false },
                            modifier = Modifier.fillMaxWidth(0.8f)
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    isMenuExpanded = false
                                    println("Edit task with ID: ${taskDetails?.id}")

                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    androidx.compose.material3.Icon(
                                        painter = painterResource(R.drawable.baseline_edit_24),
                                        contentDescription = "Edit",
                                        tint = Color.Black,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Edit Task",
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 6.dp),
                                thickness = 1.dp,
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                            DropdownMenuItem(
                                onClick = {
                                    isMenuExpanded = false
                                    searchViewModel.newTask(
                                        description = taskDetails?.description!!,
                                        time = mapToTime(taskDetails?.time!!),
                                        selectedDate = selectedDate
                                    )
                                    isTaskDetailsVisible = false
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    androidx.compose.material3.Icon(
                                        painter = painterResource(R.drawable.baseline_content_copy_24),
                                        contentDescription = "Duplicate",
                                        tint = Color.Black,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Duplicate Task",
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 6.dp),
                                thickness = 1.dp,
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                            DropdownMenuItem(
                                onClick = {
                                    isMenuExpanded = false
                                    isTaskDetailsVisible = false
                                    searchViewModel.deleteTask(taskDetails!!.id, selectedDate)
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    androidx.compose.material3.Icon(
                                        painter = painterResource(R.drawable.baseline_delete_24),
                                        contentDescription = "Delete",
                                        tint = Color.Red,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Delete Task",
                                        color = Color.Red,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = {
                                isTaskDetailsVisible = false
                            }
                        ) {
                            androidx.compose.material3.Icon(
                                painter = painterResource(R.drawable.baseline_close_24),
                                contentDescription = "Close",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Text(
                        text = taskDetails!!.description ?: "No description",
                        color = Color.White,
                        fontSize = 20.sp,
                        style = TextStyle(fontFamily = FontFamily(Font(R.font.nunito_bold)))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(

                    ) {
                        androidx.compose.material3.Icon(
                            painter = painterResource(R.drawable.baseline_access_time_24),
                            contentDescription = "Time",
                            tint = MainButtonColor,
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = mapToTime(taskDetails!!.time.toString()),
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: ApiTaskResponse, onTaskClick: (Long) -> Unit) {
    val timeText = mapToTime(task.time.toString())
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(top = 10.dp)
            .clickable { onTaskClick(task.id) },

        ) {
        Text(
            text = task.description ?: "No description",
            color = Color.White,
            fontSize = 18.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            androidx.compose.material3.Icon(
                painter = painterResource(R.drawable.baseline_access_time_24),
                contentDescription = "Time",
                tint = MainButtonColor,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = timeText,
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        thickness = 1.dp,
        color = Color.Gray.copy(alpha = 0.5f)
    )
}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}