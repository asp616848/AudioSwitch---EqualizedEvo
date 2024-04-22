package com.example.audioswitch_equalizedevo.ui.viewModels

import FetchMusic
import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.audioswitch_equalizedevo.data.ExoPlayer1
import com.example.audioswitch_equalizedevo.data.Songs
import com.example.audioswitch_equalizedevo.ui.UIState
import com.example.audioswitch_equalizedevo.ui.screenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URI
import javax.inject.Inject


@HiltViewModel
class SongsViewModel @Inject constructor(
    private val FetchMusic: FetchMusic,
    private val exoPlayer: ExoPlayer1
) : ViewModel() {
    private val _songs = MutableStateFlow<List<Songs>>(emptyList())
    val songs: StateFlow<List<Songs>> = _songs
    init {
        fetchSongs()
    }

    fun fetchSongs() {
        FetchMusic.getPlayList().let {
            _songs.value = it
        }
    }

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        exoPlayer.getExoPlayer().release()
    }
    fun playPause() {
        if(_uiState.value.isPlaying){
            _uiState.value = _uiState.value.copy(isPlaying = false)
            exoPlayer.getExoPlayer().pause()
        }
        else{
            _uiState.value = _uiState.value.copy(isPlaying = true)
            for (song in songs.value){
                exoPlayer.MediaList += MediaItem.fromUri (Uri.parse(song.fileUri))
            }
            exoPlayer.getExoPlayer().setMediaItems(exoPlayer.MediaList)
            exoPlayer.getExoPlayer().prepare()
            exoPlayer.getExoPlayer().play()
        }
    }
    fun getcurrentSong() : Songs {
        return exoPlayer.getExoPlayer().currentMediaItem?.mediaMetadata?.title?.let { title ->
            songs.value.first { it.title == title }
        } ?: songs.value.first()
    }
    fun changeScreen(s: screenState) {
        _uiState.value = _uiState.value.copy(screenState = s)
    }
}