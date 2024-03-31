package com.example.overlaydisplayapp.overlayfeature

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.example.overlaydisplayapp.overlayfeature.commons.checkAlarmSchedule
import com.example.overlaydisplayapp.overlayfeature.commons.checkNotifications
import com.example.overlaydisplayapp.overlayfeature.commons.checkOverlaySettings
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
        checkAppConditions()
        if (canStartWorker)
            startWorker()
    }

    private fun checkAppConditions() {
        checkOverlaySettings {
            canStartWorker = false
            startForResult.launch(it)
        }
        checkAlarmSchedule {
            canStartWorker = false
            startForResult.launch(it)
        }
        checkNotifications {
            canStartWorker = false
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun verifyWorker() {
        if (!checkOverlaySettings { } || !checkNotifications { } || !checkAlarmSchedule { }) {
            Toast.makeText(this, "Some permission hasn't been given yet.", Toast.LENGTH_SHORT)
                .show()
            return

        }
        startWorker()
    }
}