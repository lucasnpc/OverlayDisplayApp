package com.example.overlaydisplayapp.overlayfeature.commons

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.overlaydisplayapp.overlayfeature.worker.AlarmWorker
import java.util.concurrent.TimeUnit

fun Context.startWorker() {
    val workRequest = PeriodicWorkRequestBuilder<AlarmWorker>(24, TimeUnit.HOURS)
        .build()

    WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
        AlarmWorker.WORKER_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}

fun Context.cannotShowNotification(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.POST_NOTIFICATIONS
    ) != PackageManager.PERMISSION_GRANTED
}