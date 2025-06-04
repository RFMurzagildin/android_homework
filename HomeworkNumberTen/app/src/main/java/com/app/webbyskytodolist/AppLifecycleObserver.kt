package com.app.webbyskytodolist


import android.app.Activity
import android.app.Application
import android.os.Bundle

object AppLifecycleObserver : Application.ActivityLifecycleCallbacks {

    private var currentActivity: Activity? = null

    fun getCurrentActivity(): Activity? = currentActivity

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
        App.isInForeground = true
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity == currentActivity) {
            currentActivity = null
        }
        App.isInForeground = false
    }

    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}