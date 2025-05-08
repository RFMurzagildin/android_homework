package com.app.webbyskytodolist.data.models

import com.google.gson.annotations.SerializedName

data class RequestDate(
    @SerializedName("date")
    var date: String,
)