package com.app.webbyskytodolist.data.remote

import com.app.webbyskytodolist.data.models.ApiBaseResponse
import com.app.webbyskytodolist.data.models.ApiRequestLoginUser
import com.app.webbyskytodolist.data.models.ApiRequestRegistUser
import com.app.webbyskytodolist.data.models.ApiRequestTask
import com.app.webbyskytodolist.data.models.ApiTaskResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/registration")
    suspend fun postRegisterUser(
        @Body body: ApiRequestRegistUser,
    ): Response<ApiBaseResponse>

    @POST("/auth")
    suspend fun postLoginUser(
        @Body body: ApiRequestLoginUser,
    ): Response<ApiBaseResponse>

    @POST("/validate-token")
    suspend fun validateToken(
        @Header("Authorization") token: String,
    ): Response<ApiBaseResponse>

    @POST("/tasks/new-task")
    suspend fun newTask(
        @Header("Authorization") token: String,
        @Body body: ApiRequestTask,
    ): Response<ApiBaseResponse>

    @GET("/tasks/get-user-tasks")
    suspend fun getUserTasks(
        @Header("Authorization") token: String,
    ): Response<List<ApiTaskResponse>>

    @GET("/tasks/get-user-tasks-by-date")
    suspend fun getTasksByUserAndDate(
        @Header("Authorization") token: String,
        @Query("date") date: String,
    ): Response<List<ApiTaskResponse>>

    @GET("/tasks/get-task-details/{taskId}")
    suspend fun getTaskDetails(
        @Header("Authorization") token: String,
        @Path("taskId") taskId: Long,
    ): Response<ApiTaskResponse>
}