package com.app.webbyskytodolist

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.app.webbyskytodolist.domain.usecases.GetSavedTokenUseCase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseMessagingService@Inject constructor (
    private val getSavedTokenUseCase: GetSavedTokenUseCase
) : FirebaseMessagingService() {

    private val CHANNEL_ID = getString(R.string.default_notification_channel_id)

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val category = remoteMessage.data["category"] ?: return

        when (category) {
            "important" -> showHighPriorityNotification(remoteMessage)
            "background" -> saveToSharedPreferences(remoteMessage)
            "feature" -> handleFeatureRequest(remoteMessage)
        }
    }

    private fun showHighPriorityNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.data["title"] ?: return
        val message = remoteMessage.data["message"] ?: return

        val notificationId = System.currentTimeMillis().toInt()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_message_24)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Important Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 200, 100, 200)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notification)
    }

    private fun saveToSharedPreferences(remoteMessage: RemoteMessage) {
        val prefs = getSharedPreferences("fcm_data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("last_category", remoteMessage.data["category"])
        editor.putString("raw_data", remoteMessage.data.toString())
        editor.apply()
    }

    private fun handleFeatureRequest(remoteMessage: RemoteMessage) {
        val appContext = applicationContext as App

        if (!App.isInForeground) return

        CoroutineScope(Dispatchers.IO).launch {
            val token = try {
                getSavedTokenUseCase.invoke()
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }

            val isAuthorized = token != null
            val featureEnabled = remoteMessage.data["feature_enabled"]?.toBoolean() ?: false
            val currentActivity = AppLifecycleObserver.getCurrentActivity()
            val isOnFeatureScreen = currentActivity is FeatureActivity

            withContext(Dispatchers.Main) {
                when {
                    !isAuthorized -> appContext.showToast(appContext.getString(R.string.feature_access_denied_no_auth))
                    isOnFeatureScreen -> appContext.showToast(appContext.getString(R.string.feature_already_open))
                    !featureEnabled -> appContext.showToast(appContext.getString(R.string.feature_unavailable))
                    else -> appContext.navigateToFeatureScreen()
                }
            }
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCM", "Токен: $token")
    }
}