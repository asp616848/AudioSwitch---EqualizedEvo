package com.example.audioswitch_equalizedevo.ui.viewModels

import FetchMusic
import android.app.Application
import android.content.Context
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.audioswitch_equalizedevo.data.Songs
import com.example.audioswitch_equalizedevo.ui.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


//taken viewModel inspiration from my scramble codelab project

class SongsViewModel(application : Application) : AndroidViewModel( application) {
    private val _songs = MutableStateFlow<List<Songs>>(emptyList())
    val songs: StateFlow<List<Songs>> = _songs
    init {
        fetchSongs(application)
    }

    val exoPlayer = ExoPlayer.Builder(application).build()
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
            exoPlayer?.pause()
        }
        else{
            _uiState.value = _uiState.value.copy(isPlaying = true)
            val mediaItem = MediaItem.fromUri(this.songs.value.first().fileUri)
            exoPlayer?.setMediaItem(mediaItem)
            exoPlayer?.prepare()
            exoPlayer?.play()
        }
    }
    fun getcurrentSong() : Songs {
        return exoPlayer.currentMediaItem?.mediaMetadata?.title?.let { title ->
            songs.value.first { it.title == title }
        } ?: songs.value.first()
    }
    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
    }
}