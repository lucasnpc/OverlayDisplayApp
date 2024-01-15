package com.example.overlaydisplayapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.overlaydisplayapp.overlayfeature.notification.OverlayNotification

class OverlayApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            OverlayNotification.CHANNEL_ID,
            OverlayNotification.NAME, NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}