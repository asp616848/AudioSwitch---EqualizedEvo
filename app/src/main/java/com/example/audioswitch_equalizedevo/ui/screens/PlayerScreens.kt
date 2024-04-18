package com.example.audioswitch_equalizedevo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Pause
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun PlayerCompact(viewModel: SongsViewModel){
    IconButton(onClick = { /*TODO*/ }) {

        if(viewModel.uiState.value.isPlaying) {
            Icon(Icons.TwoTone.Pause, contentDescription = "Play", modifier = Modifier.clickable(onClick = {
                viewModel.playPause()
            }))
        }else{
            Icon(Icons.TwoTone.PlayArrow, contentDescription = "Pause")
        }
    }
}