package com.example.overlaydisplayapp.overlayfeature.commons

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.overlaydisplayapp.overlayfeature.worker.AlarmWorker
import java.util.concurrent.TimeUnit

fun Context.startWorker() {
    val workRequest = PeriodicWorkRequestBuilder<AlarmWorker>(1, TimeUnit.DAYS)
        .build()

    WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
        AlarmWorker.WORKER_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}