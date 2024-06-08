package com.example.audioswitch_equalizedevo.ui.screens

import android.net.Uri
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.twotone.Pause
import androidx.compose.material.icons.twotone.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.ui.PlayerView
import androidx.media3.ui.R
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.audioswitch_equalizedevo.ui.screenState
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PlayerCompact(navController: NavController, viewModel: SongsViewModel, animatedVisibilityScope: AnimatedVisibilityScope) {
    var playing by rememberSaveable { mutableStateOf(viewModel.uiState.value.isPlaying) }
    val uiState by viewModel.uiState.collectAsState()
    var currSong by remember {
        mutableStateOf(viewModel.getcurrentSong())
    }
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
        Box(
            modifier = Modifier.size(60.dp),
            contentAlignment = Alignment.Center
        ){
            Icon(
                Icons.Default.MusicNote,
                contentDescription = "Default Thumbnail",
                modifier = Modifier.size(40.dp),
                tint = Color.Magenta
            )
            Image(  //shared element modifier for shared element transition only for image
                painter = // Enable crossfade animation
                rememberAsyncImagePainter(
                    ImageRequest.Builder // Placeholder while loading
                        (LocalContext.current).data(data = Uri.parse(currSong.albumArt))
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(true) // Enable crossfade animation
                            placeholder(R.drawable.exo_ic_audiotrack) // Placeholder while loading
                        }).build()
                ),
                contentDescription = "Song Icon",
                Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = "image"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .size(40.dp)
            )
        }

        // Text
        Column(modifier = Modifier
            .weight(1f)
            .offset(x = 6.dp)) {

            Text(text = currSong.title,
                Modifier.sharedElement(
                    state = rememberSharedContentState(key = "text"),
                    animatedVisibilityScope = animatedVisibilityScope
                ))
            Text(text = currSong.artist)
        }


        // Previous Button
        IconButton(
            onClick = { viewModel.exoPlayer.playPrev() },
            modifier = Modifier
                .size(35.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "prev"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        ) {
            Icon(Icons.Filled.SkipPrevious, contentDescription = "Previous")
        }

        // Play/Pause Button
        IconButton(
            onClick = { viewModel.playPause() },
            modifier = Modifier
                .size(35.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "pause"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
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
            modifier = Modifier
                .size(35.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "next"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        ) {
            Icon(Icons.Filled.SkipNext, contentDescription = "Next")
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PlayerScreen(navController: NavHostController, viewModel: SongsViewModel, animatedVisibilityScope: AnimatedVisibilityScope ) {
    var playing by rememberSaveable { mutableStateOf(viewModel.uiState.value.isPlaying) }
    val uiState by viewModel.uiState.collectAsState()
    var currSong by remember {
        mutableStateOf(viewModel.getcurrentSong())
    }
    LaunchedEffect(viewModel.getcurrentSong()) {
        currSong = viewModel.getcurrentSong()
    }
    LaunchedEffect(uiState.isPlaying) {
        playing = uiState.isPlaying
    }

    Column (modifier = Modifier
        .padding(100.dp)
        .fillMaxSize()  ) {
        Image(  //shared element modifier for shared element transition only for image
            painter = // Enable crossfade animation
            rememberAsyncImagePainter(ImageRequest.Builder // Placeholder while loading
                (LocalContext.current).data(data = Uri.parse(currSong.albumArt)).apply(block = fun ImageRequest.Builder.() {
                crossfade(true) // Enable crossfade animation
                placeholder(R.drawable.exo_ic_audiotrack) // Placeholder while loading
            }).build()
            ),
            contentDescription = "Song Icon",
            modifier = Modifier
                .requiredSize(270.dp)
                .padding(4.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "image"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
        Text(text = currSong.title,
            Modifier
                .fillMaxWidth()
                .sharedElement(
                    state = rememberSharedContentState(key = "text"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
        Row(Modifier.fillMaxWidth()) {
            Controls(viewModel, animatedVisibilityScope)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.Controls(viewModel: SongsViewModel, animatedVisibilityScope: AnimatedVisibilityScope) {
    var playing by rememberSaveable { mutableStateOf(viewModel.uiState.value.isPlaying) }
    val uiState by viewModel.uiState.collectAsState()
    var currSeek:Long = 0

    //seek touch and slide bar composable goes here TODO

    LaunchedEffect(uiState.isPlaying) {
        playing = uiState.isPlaying
    }
    LaunchedEffect(uiState.seekVal) {
        currSeek = uiState.seekVal.toLong()
    }

    Column{// Previous Button
        Row{
            IconButton(
                onClick = { viewModel.exoPlayer.playPrev() },
                modifier = Modifier
                    .size(35.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "prev"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            ) {
                Icon(Icons.Filled.SkipPrevious, contentDescription = "Previous")
            }
            IconButton(
                onClick = { viewModel.playPause() },
                modifier = Modifier
                    .size(45.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "pause"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
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
                modifier = Modifier
                    .size(35.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "next"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            ) {
                Icon(Icons.Filled.SkipNext, contentDescription = "Next")
            }
        }
        Slider(value = currSeek.toFloat(), onValueChange = { viewModel.seekTo(it)
        currSeek = it.toLong()})
    }


}