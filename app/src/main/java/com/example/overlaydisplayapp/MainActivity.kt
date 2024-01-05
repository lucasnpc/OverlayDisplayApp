package com.example.overlaydisplayapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.overlaydisplayapp.ui.theme.OverlayDisplayAppTheme

class MainActivity : ComponentActivity() {
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Permission guaranteed", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Settings.canDrawOverlays(this)) {
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            ).also {
                startForResult.launch(it)
            }
        }
        setContent {
            OverlayDisplayAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            Button(onClick = {
                                Intent(this@MainActivity, OverlayService::class.java).also {
                                    it.action = OverlayActions.START.name
                                    startService(it)
                                }
                            }) {
                                Text(text = "StartService Overlay")
                            }
                            Button(onClick = {
                                Intent(this@MainActivity, OverlayService::class.java).also {
                                    it.action = OverlayActions.STOP.name
                                    startService(it)
                                }
                            }) {
                                Text(text = "StopService Overlay")
                            }
                        }
                    }
                }
            }
        }
    }
}