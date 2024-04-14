package com.example.audioswitch_equalizedevo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.audioswitch_equalizedevo.data.Songs
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel

@Composable
fun SongsScreen(paddingValues: PaddingValues, viewModel: SongsViewModel) {

    val songList by viewModel.songs.collectAsState()

    Column{
        LazyColumn(contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.large)
        ) {
            if (songList.isNotEmpty()) {
                items(songList) { song ->  /* can't use foreach here since
     it's not a composable lambda function but items returns a composable lambda function*/
                    SongRow(song = song)
                }
            } else {
                item {
                    Column {
                        Text(
                            text = "\nNo Songs Found",
                            color = MaterialTheme.colorScheme.onError,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun SongRow(song: Songs) {

    Row(modifier = Modifier.clickable(onClick = {})){
        Image(
            Icons.Outlined.MusicNote,
            contentDescription = "Thumbnail",
            modifier = Modifier.requiredSize(64.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = song.title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = song.artist,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        IconButton(onClick = { }) {
            Icon(Icons.Sharp.MoreVert, contentDescription = "More Options", modifier = Modifier.weight(1f))
        }
    }
}


