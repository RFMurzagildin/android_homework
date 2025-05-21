package com.app.webbyskytodolist.data.repositories

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.app.webbyskytodolist.domain.repositories.AuthRepository

class AuthRepositoryImpl(private val context: Context) : AuthRepository {
    private val encryptedSharedPreferences by lazy {
        val mainKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "auth_prefs",
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override suspend fun saveToken(token: String) {
        encryptedSharedPreferences.edit().putString("jwt_token", token).apply()
    }

    override suspend fun logoutUser() {
        encryptedSharedPreferences.edit().remove("jwt_token").apply()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return encryptedSharedPreferences.contains("jwt_token")
    }

    override suspend fun getSavedToken(): String? {
        return encryptedSharedPreferences.getString("jwt_token", null)
    }
}