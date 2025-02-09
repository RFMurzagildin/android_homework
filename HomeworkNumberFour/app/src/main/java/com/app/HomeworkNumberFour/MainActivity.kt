package com.app.HomeworkNumberFour

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.HomeworkNumberFour.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val PICK_IMAGE_REQUEST = 1

    private var notificationManager: NotificationManager? = null
    private val channelId = "notification_channel"
    private var recyclerView: RecyclerView? = null

    private val colors = listOf(
        Pair(Color.RED, "Theme.Themes_notifications.Red"),
        Pair(Color.GREEN, "Theme.Themes_notifications.Green"),
        Pair(Color.YELLOW, "Theme.Themes_notifications.Yellow")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Установите тему перед отображением активности
        setTheme(getSelectedTheme())
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        recyclerView = findViewById(R.id.recyclerViewColors)
        val adapter = ColorAdapter(colors.map { it.first }) { color ->
            val theme = colors.first { it.first == color }.second
            setAppTheme(theme)
        }

        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView?.adapter = adapter

        if (intent.getBooleanExtra("from_notification", false)) {
            Toast.makeText(this, getString(R.string.launched_from_notification), Toast.LENGTH_SHORT).show()
        }
        checkNotificationPermission()
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        creatingSpinnerAdapter()
        binding?.run {
            ivMainRoundPhoto.setOnClickListener {
                openGallery()
            }
            ivDeletePhoto.setOnClickListener {
                resetImageView()
            }
            showNotification.setOnClickListener {
                val title = binding?.tietNotificationTitle?.text.toString()
                val message = binding?.tietNotificationMessage?.text.toString()
                val priority = binding?.spinnerPriority?.selectedItem.toString()
                if (title.isEmpty() || message.isEmpty()) {
                    Toast.makeText(this@MainActivity,
                        getString(R.string.fill_in_all_the_text_fields), Toast.LENGTH_SHORT).show()
                } else {
                    showNotification(title, message, priority)
                }
            }
            openRecycler.setOnClickListener {
                binding?.recyclerViewColors?.visibility = View.VISIBLE
                it.visibility = View.GONE
                binding?.closeRecycler?.visibility = View.VISIBLE
            }
            closeRecycler.setOnClickListener {
                binding?.recyclerViewColors?.visibility = View.GONE
                it.visibility = View.GONE
                binding?.openRecycler?.visibility = View.VISIBLE
            }
            resetColor.setOnClickListener {
                setAppTheme("Theme.HomeworkNumberFour")
                recreate()
            }
        }
    }

    private fun setAppTheme(theme: String) {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        sharedPreferences.edit().putString("APP_THEME", theme).apply()
        recreate()
    }

    private fun getSelectedTheme(): Int {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        return when (sharedPreferences.getString("APP_THEME", "Theme.HomeworkNumberFour")) {
            "Theme.Themes_notifications.Red" -> R.style.Theme_Themes_notifications_Red
            "Theme.Themes_notifications.Green" -> R.style.Theme_Themes_notifications_Green
            "Theme.Themes_notifications.Yellow" -> R.style.Theme_Themes_notifications_Yellow
            else -> R.style.Theme_HomeworkNumberFour
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun creatingSpinnerAdapter(){
        ArrayAdapter.createFromResource(
            this,
            R.array.priority_levels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding?.spinnerPriority?.adapter = adapter
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            binding?.ivMainRoundPhoto?.setCircularImage(bitmap)
            binding?.ivDeletePhoto?.visibility = ImageView.VISIBLE
        }
    }

    private fun resetImageView() {
        binding?.ivMainRoundPhoto?.setImageResource(R.drawable.image_placeholder)
        binding?.ivDeletePhoto?.visibility = ImageView.GONE
    }

    fun ImageView.setCircularImage(bitmap: Bitmap) {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val radius = Math.min(bitmap.width / 2f, bitmap.height / 2f)

        paint.isAntiAlias = true
        canvas.drawCircle(bitmap.width / 2f, bitmap.height / 2f, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        this.setImageBitmap(output)
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, "Main Channel", importance)
            notificationManager?.createNotificationChannel(channel)
        }
    }
    private fun showNotification(title: String, message: String, priority: String) {
        val importance = when (priority) {
            "Max" -> NotificationManager.IMPORTANCE_HIGH
            "High" -> NotificationManager.IMPORTANCE_DEFAULT
            "Default" -> NotificationManager.IMPORTANCE_LOW
            "Low" -> NotificationManager.IMPORTANCE_MIN
            else -> NotificationManager.IMPORTANCE_DEFAULT
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("from_notification", true) // Добавляем флаг
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.baseline_3p_24)
            .setPriority(importance)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(1, notification)

        Toast.makeText(this, getString(R.string.the_notification_has_been_sent), Toast.LENGTH_SHORT).show()
    }
    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                    getString(R.string.permission_to_send_notifications_has_been_received), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,
                    getString(R.string.permission_to_send_notifications_is_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }

}