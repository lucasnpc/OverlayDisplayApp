package com.example.overlaydisplayapp.overlayfeature.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.overlaydisplayapp.R
import com.example.overlaydisplayapp.overlayfeature.OverlayActivity
import com.example.overlaydisplayapp.overlayfeature.commons.checkNotifications

class OverlayNotification {

    @SuppressLint("MissingPermission")
    fun build(context: Context) {
        if (!context.checkNotifications { }) {
            return
        }

        val notificationIntent = Intent(context, OverlayActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_android)
            .setContentTitle(context.getString(R.string.overlay_notificiation_title))
            .setContentText(context.getString(R.string.overlay_notification_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 123
        const val CHANNEL_ID = "112233"
        const val NAME = "OverlayNotification"
    }
}