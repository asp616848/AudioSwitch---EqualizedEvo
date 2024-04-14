package com.example.audioswitch_equalizedevo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel

@Composable
fun SongsScreen(paddingValues: PaddingValues, viewModel: SongsViewModel) {

    val songList by viewModel.songs.collectAsState()

    LazyColumn(contentPadding = paddingValues){
        if(songList.isNotEmpty()){
            items(songList) { song ->  /* can't use foreach here since
         it's not a composable lambda function but items returns a composable lambda function*/
                Text(text = song.title)
            }
        }else{
            item {
                Column {
                    Text(text = "\nNo Songs Found")
                }
            }
        }
    }
}