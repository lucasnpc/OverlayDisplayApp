package com.example.overlaydisplayapp.overlayfeature.alarmscheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import com.example.overlaydisplayapp.overlayfeature.receivers.OverlayReceiver
import java.util.Calendar

class OverlayAlarmScheduler(private val context: Context) : AlarmScheduler {
    private val alarmManager by lazy {
        context.getSystemService(ALARM_SERVICE) as AlarmManager
    }

    override fun schedule() {
        val intent = Intent(context, OverlayReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 15)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    override fun cancel() {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                ALARM_ID,
                Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    companion object {
        const val ALARM_ID = 1
    }
}