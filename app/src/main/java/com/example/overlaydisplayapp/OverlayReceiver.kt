package com.example.overlaydisplayapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings

class OverlayReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (!Settings.canDrawOverlays(context)) {
            Intent(context, MainActivity::class.java).also {
                context?.startActivity(it)
            }
            return
        }
        Intent(context, OverlayService::class.java).also {
            it.action = OverlayActions.START.name
            context?.startService(it)
        }
    }
}