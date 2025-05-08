package com.app.webbyskytodolist.domain.repositories

interface AuthRepository {
    suspend fun saveToken(token: String)
    suspend fun logoutUser()
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getSavedToken(): String?
}