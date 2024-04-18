package com.example.audioswitch_equalizedevo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.twotone.Pause
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel

//implement using constraint layout for practice
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlayerCompact(viewModel: SongsViewModel) {
    var playing by rememberSaveable { mutableStateOf(viewModel.uiState.value.isPlaying) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isPlaying) {
        playing = uiState.isPlaying
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = {})
    ) {
        // Song Icon
        Icon(
            Icons.Filled.MusicNote,
            contentDescription = "Song Icon",
            modifier = Modifier.size(35.dp)
        )

        // Text
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Song Title")
            Text(text = "Artist Name")
        }

        // Previous Button
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(35.dp)
        ) {
            Icon(Icons.Filled.SkipPrevious, contentDescription = "Previous")
        }

        // Play/Pause Button
        IconButton(
            onClick = { viewModel.playPause() },
            modifier = Modifier.size(35.dp)
        ) {
            // Use the playing state to decide which icon to display
            val icon = if (playing) Icons.TwoTone.Pause else Icons.TwoTone.PlayArrow
            Icon(
                icon,
                contentDescription = if (playing) "Pause" else "Play",
            )
        }

        // Next Button
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.size(35.dp)
        ) {
            Icon(Icons.Filled.SkipNext, contentDescription = "Next")
        }
    }
}
