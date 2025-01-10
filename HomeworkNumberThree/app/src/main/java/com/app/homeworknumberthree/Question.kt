package com.app.homeworknumberthree

data class Question(
    val questionText: String,
    val options: MutableList<String>,
    var markedIndex: Int = -1,
)
