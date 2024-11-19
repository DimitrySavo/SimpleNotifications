package com.example.simplenotifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var manager: NotificationManagerCompat
    companion object {
        val NORMAL_CHANNEL = "NORMAL_CHANNEL"
        val IMPORTANT_CHANNEL = "IMPORTANT_CHANNEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        manager = NotificationManagerCompat.from(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = resources.getString(R.string.NOT_IMPORTANT_CHANNEL_NAME)
            val channel = NotificationChannel(
                NORMAL_CHANNEL,
                name,
                NotificationManager.IMPORTANCE_LOW
            )
            val description = resources.getString(R.string.NOT_IMPORTANT_CHANNEL_DESCRIPTION)
            channel.description = description
            channel.enableVibration(false)
            manager.createNotificationChannel(channel)
        }
    }

    fun simpleNotification(view: View) {
        val builder = NotificationCompat.Builder(
            this,
            NORMAL_CHANNEL
        )
        builder
            .setSmallIcon(android.R.drawable.btn_star)
            .setContentTitle("Простое оповещение")
            .setContentText("Что-то важное произошло")
            .setLargeIcon(
                BitmapFactory.decodeResource(resources,
                    R.drawable.ic_launcher_background)
            )
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i("Notification", "Cant create simple notification")
            return
        }

        manager.notify(R.id.SIMPLE_NOTIFICATION_ID, builder.build())

    }

    fun askPermission(): Boolean {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


    }
}