package com.example.overlaydisplayapp.overlayfeature.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.overlaydisplayapp.overlayfeature.alarmscheduler.OverlayAlarmScheduler

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED && context != null) {
            OverlayAlarmScheduler(context).schedule()
        }
    }
}