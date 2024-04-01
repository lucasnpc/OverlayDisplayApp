package com.example.overlaydisplayapp.overlayfeature.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.example.overlaydisplayapp.overlayfeature.OverlayActions
import com.example.overlaydisplayapp.overlayfeature.notification.OverlayNotification
import com.example.overlaydisplayapp.overlayfeature.service.OverlayService


class OverlayReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (!Settings.canDrawOverlays(context)) {
            context?.let { OverlayNotification().build(it) }
            return
        }
        Intent(context, OverlayService::class.java).also {
            it.action = OverlayActions.START.name
            context?.startForegroundService(it)
        }
    }
}