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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.audioswitch_equalizedevo.ui.screens.HomeScreen
import com.example.audioswitch_equalizedevo.ui.theme.AudioSwitch_EqualizedEvoTheme
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val readAudioPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        Manifest.permission.READ_MEDIA_AUDIO
    else
        Manifest.permission.READ_EXTERNAL_STORAGE

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission is required to access Music Files.", Toast.LENGTH_SHORT).show()
                ActivityCompat.finishAffinity(this)
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
        val viewModel: SongsViewModel = SongsViewModel()
        viewModel.fetchSongs(context = this)
        viewModel.initializeExoPlayer(context = this)
        setContent {
            AudioSwitch_EqualizedEvoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(viewModel)
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                readAudioPermission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissionLauncher.launch(readAudioPermission)
        }
        else{
            Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
        }
    }
}

