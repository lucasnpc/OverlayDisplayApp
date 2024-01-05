package com.example.overlaydisplayapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

class OverlayAlarmScheduler(private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    private val id by lazy {
        UUID.randomUUID().hashCode()
    }

    override fun schedule() {
        val intent = Intent(context, OverlayReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            60000,
            pendingIntent
        )
    }

    override fun cancel() {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                id,
                Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}