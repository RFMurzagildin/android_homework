package com.app.webbyskytodolist.data.models

import com.google.gson.annotations.SerializedName

data class ApiTaskResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("time")
    val time: String? = null,
)