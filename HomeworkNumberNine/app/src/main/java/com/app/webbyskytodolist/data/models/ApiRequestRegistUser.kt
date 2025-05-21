package com.app.webbyskytodolist.data.models

import com.google.gson.annotations.SerializedName

data class ApiRequestRegistUser(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("confirmPassword")
    val confirmPassword: String? = null,
)
