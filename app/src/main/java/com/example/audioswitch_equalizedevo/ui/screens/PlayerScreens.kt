package com.example.audioswitch_equalizedevo.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Pause
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PlayerCompact(viewModel: SongsViewModel){
    val uiState = viewModel.uiState.collectAsState()

    Row(modifier = Modifier.clickable(onClick = {

    })){
        IconButton(onClick = { /*TODO*/ }) {
            if (uiState.value.isPlaying) {
                Icon(
                    Icons.TwoTone.Pause,
                    contentDescription = "Play",
                    modifier = Modifier.clickable(onClick = {
                        viewModel.playPause()
                    })
                )
            } else {
                Icon(
                    Icons.TwoTone.PlayArrow,
                    contentDescription = "Pause",
                    modifier = Modifier.clickable(onClick = {
                        viewModel.playPause()
                    })
                )
            }
        }

    }
}