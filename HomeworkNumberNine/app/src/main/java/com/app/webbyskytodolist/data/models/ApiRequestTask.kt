package com.app.webbyskytodolist.data.models

import com.google.gson.annotations.SerializedName

data class ApiRequestTask(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("time")
    val time: String? = null,
)