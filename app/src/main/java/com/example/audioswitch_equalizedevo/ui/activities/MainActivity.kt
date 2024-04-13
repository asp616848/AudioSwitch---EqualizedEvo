package com.example.audioswitch_equalizedevo.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.audioswitch_equalizedevo.ui.screens.HomeScreen
import com.example.audioswitch_equalizedevo.ui.theme.AudioSwitch_EqualizedEvoTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        // For Android 11+ (API level 30) and up
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            null
        }
    ).filterNotNull().toTypedArray() // Remove null elements for pre-Android 11

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted ->
            if (isGranted.all { it.value }) { // Check all permissions granted using extension function
                // Permission granted, proceed with app functionality
                setContent {
                    AudioSwitch_EqualizedEvoTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            HomeScreen()
                        }
                    }
                }
            } else {
                // Permission denied, explain to user and handle gracefully
                Toast.makeText(this, "Storage permissions are required to use the app.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        checkPermissions()
    }

    private fun checkPermissions() {
        val permissionsToRequest = getPermissionsToRequest(REQUIRED_PERMISSIONS)
        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest)
        } else {
            // All permissions already granted, proceed with app functionality
            setContent {
                AudioSwitch_EqualizedEvoTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HomeScreen()
                    }
                }
            }
        }
    }

    private fun getPermissionsToRequest(permissions: Array<String>): Array<String> {
        return permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
    }
}
