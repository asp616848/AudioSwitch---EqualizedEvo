package com.example.audioswitch_equalizedevo.ui.viewModels

import FetchMusic
import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import com.example.audioswitch_equalizedevo.data.Songs
import com.example.audioswitch_equalizedevo.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


//taken viewModel inspiration from my scramble codelab project

class SongsViewModel : ViewModel() {
    private val _songs = MutableStateFlow<List<Songs>>(emptyList())
    val songs: StateFlow<List<Songs>> = _songs
    fun fetchSongs(context: Context) {
        FetchMusic().getPlayList(context ).let {
            _songs.value = it

        }
    }

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()
    fun playPause() {
        if(_uiState.value.isPlaying){
            _uiState.value = _uiState.value.copy(isPlaying = false)
        }
        else{
            _uiState.value = _uiState.value.copy(isPlaying = true)
        }
        val mediaItem = MediaItem.fromUri(this.songs.value.first().fileUri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }
}