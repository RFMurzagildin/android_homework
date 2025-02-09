package com.app.homework_number_six

import android.app.Application
import com.app.homework_number_six.di.ServiceLocator

class App : Application() {
    private val serviceLocator = ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator.initDataLayerDependencies(ctx = this)
    }
}