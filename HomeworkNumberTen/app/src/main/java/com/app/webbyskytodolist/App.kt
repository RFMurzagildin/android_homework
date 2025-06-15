package com.app.webbyskytodolist

import android.app.Application
import android.content.Intent
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var instance: App
        var isInForeground = false
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(AppLifecycleObserver)
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun navigateToFeatureScreen() {
        FeatureActivity.openFeatureScreen(this)
    }
}