package com.example.audioswitch_equalizedevo.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel

@Composable
fun SongsScreen(paddingValues: PaddingValues, context: android.content.Context) {
    Text(text = "Songs Screen", modifier = Modifier.padding(paddingValues))

    val viewModel: SongsViewModel = SongsViewModel()
    val songList by viewModel.songs.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetchSongs(context = context)
    }

    LazyColumn(contentPadding = paddingValues){
        items(songList) { song ->  /* can't use foreach here since
         it's not a composable lambda function but items returns a composable lambda function*/
            Text(text = song.title)
        }
    }
}