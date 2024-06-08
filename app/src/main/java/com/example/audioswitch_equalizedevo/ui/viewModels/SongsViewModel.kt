package com.example.audioswitch_equalizedevo.ui.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import com.example.audioswitch_equalizedevo.data.ExoPlayer1
import com.example.audioswitch_equalizedevo.data.FetchMusic
import com.example.audioswitch_equalizedevo.data.Songs
import com.example.audioswitch_equalizedevo.ui.UIState
import com.example.audioswitch_equalizedevo.ui.screenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SongsViewModel @Inject constructor(
    val fetchMusic: FetchMusic,
    val exoPlayer: ExoPlayer1
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()

    private val _songs = MutableStateFlow<List<Songs>>(emptyList())
    val songs: StateFlow<List<Songs>> = _songs
    init {
        fetchSongs()
    }

    fun seekTo(value: Float){
        exoPlayer.getExoPlayer().seekTo(value.toLong())
        _uiState.value = _uiState.value.copy(seekVal = value)
        Log.d("abc", "KUCH ${_uiState.value.seekVal} : ${uiState.value.seekVal} : ${value.toLong()} : ${value}")
    }

    fun fetchSongs() {
        fetchMusic.getPlayList().let {
            _songs.value = it.distinct()
        }
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.getExoPlayer().release()
    }
    fun playPause() {
        if(exoPlayer.getExoPlayer().isPlaying){
            _uiState.value = _uiState.value.copy(seekVal = exoPlayer.getExoPlayer().currentPosition.toFloat(), isPlaying = false) //TODO
            exoPlayer.getExoPlayer().pause()
        }
        else {
            for (song in songs.value) {
                exoPlayer.MediaList += MediaItem.fromUri(Uri.parse(song.fileUri))
            }
            exoPlayer.getExoPlayer().setMediaItems(exoPlayer.MediaList)
            exoPlayer.getExoPlayer().prepare()
            exoPlayer.getExoPlayer().seekTo(_uiState.value.seekVal.toLong())
            exoPlayer.getExoPlayer().play()
            _uiState.value = _uiState.value.copy(isPlaying = true, songId = exoPlayer.getExoPlayer().currentMediaItem?.localConfiguration?.uri.toString())
        }
    }
    fun playSong(song: Songs) {  //on ROW TAP
        exoPlayer.getExoPlayer().pause()
        exoPlayer.MediaList = emptyList()
        exoPlayer.MediaList += MediaItem.fromUri (Uri.parse(song.fileUri))
        exoPlayer.getExoPlayer().setMediaItems(exoPlayer.MediaList)
        exoPlayer.getExoPlayer().prepare()
        exoPlayer.getExoPlayer().play()
        _uiState.value = _uiState.value.copy(seekVal = 0F, isPlaying = true, songId = exoPlayer.getExoPlayer().currentMediaItem?.localConfiguration?.uri.toString())
    }
    fun getcurrentSong(): Songs {
        Log.e("getCurr", "INVOKED")

        if (songs.value.isNotEmpty()) {
            Log.d("getCurr", "Songs list size: ${songs.value.size}")

            val exoPlayer = exoPlayer.getExoPlayer()
            val currentMediaItem = exoPlayer.currentMediaItem
            val title = currentMediaItem?.localConfiguration?.uri.toString()

            Log.d("getCurr", "Current media item title: $title")

            val matchedSong = songs.value.firstOrNull {
                Log.d("getCurr", "Checking song: ${it.fileUri}")
                it.fileUri == title
            }

            return matchedSong ?: Songs(title = "No Song selected")
        } else {
            Log.e("getCurr", "Songs list is empty")
            return Songs(title = "No Song selected")
        }
    }

    fun changeScreen(s: screenState) {
        _uiState.value = _uiState.value.copy(screenState = s)
    }
}