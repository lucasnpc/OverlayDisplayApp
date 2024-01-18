package com.example.overlaydisplayapp.overlayfeature

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.example.overlaydisplayapp.overlayfeature.commons.cannotShowNotification
import com.example.overlaydisplayapp.overlayfeature.commons.startWorker

class OverlayActivity : ComponentActivity() {

    private var canStartWorker = true

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            verifyWorker()
        }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            verifyWorker()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handlePermissions()
        if (canStartWorker)
            startWorker()
    }

    private fun handlePermissions() {
        if (!Settings.canDrawOverlays(this)) {
            canStartWorker = false
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            ).also {
                startForResult.launch(it)
            }
        }
        if (cannotShowNotification()) {
            canStartWorker = false
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun verifyWorker() {
        if (Settings.canDrawOverlays(this)) {
            if (cannotShowNotification()) {
                return
            }

            startWorker()
        }
    }
}