package com.app.webbyskytodolist

import android.annotation.SuppressLint
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await

object RemoteConfigManager {

    private val remoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val defaults = mapOf(
            "feature_enabled" to false
        )
        remoteConfig.setDefaultsAsync(defaults)
    }

    suspend fun fetchFeatureFlag(): Boolean {
        return try {
            remoteConfig.fetchAndActivate().await()
            remoteConfig.getBoolean("feature_enabled")
        } catch (e: Exception) {
            false
        }
    }
}