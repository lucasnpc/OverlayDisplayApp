package com.example.overlaydisplayapp.overlayfeature.commons

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat

fun Context.checkOverlaySettings(callback: (Intent) -> Unit): Boolean {
    return if (!Settings.canDrawOverlays(this)) {
        Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        ).also {
            callback(it)
        }
        false
    } else true
}

fun Context.checkAlarmSchedule(callback: (Intent) -> Unit): Boolean {
    val alarmManager = getSystemService(ComponentActivity.ALARM_SERVICE) as AlarmManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && !alarmManager.canScheduleExactAlarms()) {
        Intent(
            Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
            Uri.parse("package:$packageName")
        ).also {
            callback(it)
        }
        false
    } else true
}

fun Context.checkNotifications(callback: () -> Unit): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        callback()
        false
    } else {
        true
    }
}