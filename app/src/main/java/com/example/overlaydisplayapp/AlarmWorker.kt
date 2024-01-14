package com.example.overlaydisplayapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class AlarmWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val alarmScheduler: AlarmScheduler by lazy {
        OverlayAlarmScheduler(appContext)
    }

    override fun doWork(): Result {
        alarmScheduler.schedule()
        return Result.success()
    }

    companion object{
        const val WORKER_NAME = "OverlayAlarm"
    }
}