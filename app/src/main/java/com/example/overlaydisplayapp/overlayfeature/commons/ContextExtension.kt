package com.example.overlaydisplayapp.overlayfeature.commons

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.overlaydisplayapp.overlayfeature.worker.AlarmWorker
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.checkNotificationPermission(): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.POST_NOTIFICATIONS
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.startWorker() {
    val workRequest = PeriodicWorkRequestBuilder<AlarmWorker>(24, TimeUnit.HOURS)
        .build()

    WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
        AlarmWorker.WORKER_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}