package com.app.webbyskytodolist.data.models

import com.google.gson.annotations.SerializedName

data class ApiBaseResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
)