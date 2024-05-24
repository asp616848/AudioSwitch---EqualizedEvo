package com.example.audioswitch_equalizedevo.ui.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.ui.R
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.audioswitch_equalizedevo.ui.screenState
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel

@Composable
fun PlayerCompact(navController: NavController, viewModel: SongsViewModel) {
    var playing by rememberSaveable { mutableStateOf(viewModel.uiState.value.isPlaying) }
    val uiState by viewModel.uiState.collectAsState()
    var currSong by remember {
        mutableStateOf(viewModel.getcurrentSong())
    }
    Log.d("currSong", "DDDDDDDcurrSong: ${uiState.songId}")
    LaunchedEffect(viewModel.getcurrentSong()) {
        currSong = viewModel.getcurrentSong()
    }
    LaunchedEffect(uiState.isPlaying) {
        playing = uiState.isPlaying
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = {
                navController.navigate("player")
                viewModel.changeScreen(screenState.PLAYER)
            })
            .padding(6.dp)
    ) {
        // Song Icon

        Image(
            painter = // Enable crossfade animation
            rememberAsyncImagePainter(ImageRequest.Builder // Placeholder while loading
                (LocalContext.current).data(data = Uri.parse(currSong.albumArt)).apply(block = fun ImageRequest.Builder.() {
                crossfade(true) // Enable crossfade animation
                placeholder(R.drawable.exo_ic_audiotrack) // Placeholder while loading
            }).build()
            ),
            contentDescription = "Song Icon",
            modifier = Modifier
                .requiredSize(60.dp)
                .padding(4.dp)
        )

        // Text
        Column(modifier = Modifier
            .weight(1f)
            .offset(x = 6.dp)) {

            Text(text = currSong.title)
            Text(text = currSong.artist)
        }


        // Previous Button
        IconButton(
            onClick = { viewModel.exoPlayer.playPrev() },
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
            onClick = { viewModel.exoPlayer.playNext() },
            modifier = Modifier.size(35.dp)
        ) {
            Icon(Icons.Filled.SkipNext, contentDescription = "Next")
        }
    }
}

@Composable
fun PlayerScreen(navController: NavHostController, viewModel: SongsViewModel) {
    Column (modifier = Modifier.padding(126.dp)  ) {
        Text(text = "Player Screen")
    }
}