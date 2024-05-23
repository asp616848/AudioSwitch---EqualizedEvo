package com.example.audioswitch_equalizedevo.ui.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
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
    private val _songs = MutableStateFlow<List<Songs>>(emptyList())
    val songs: StateFlow<List<Songs>> = _songs
    init {
        fetchSongs()
    }

    fun fetchSongs() {
        fetchMusic.getPlayList().let {
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
        if(exoPlayer.getExoPlayer().isPlaying){
            _uiState.value = _uiState.value.copy(seekVal = exoPlayer.getExoPlayer().currentPosition, isPlaying = false)
            exoPlayer.getExoPlayer().pause()
        }
        else {
            for (song in songs.value) {
                exoPlayer.MediaList += MediaItem.fromUri(Uri.parse(song.fileUri))
            }
            exoPlayer.getExoPlayer().setMediaItems(exoPlayer.MediaList)
            exoPlayer.getExoPlayer().prepare()
            exoPlayer.getExoPlayer().seekTo(_uiState.value.seekVal)
            exoPlayer.getExoPlayer().play()
            _uiState.value = _uiState.value.copy(isPlaying = true)
        }
    }
    fun playSong(song: Songs) {  //on ROW TAP
        exoPlayer.getExoPlayer().pause()
        exoPlayer.MediaList = emptyList()
        exoPlayer.MediaList += MediaItem.fromUri (Uri.parse(song.fileUri))
        exoPlayer.getExoPlayer().setMediaItems(exoPlayer.MediaList)
        exoPlayer.getExoPlayer().prepare()
        exoPlayer.getExoPlayer().play()
        _uiState.value = _uiState.value.copy(seekVal = 0, isPlaying = true)
    }
    fun getcurrentSong(): Songs {
        if(!songs.value.isEmpty()){
            return exoPlayer.getExoPlayer().currentMediaItem?.mediaMetadata?.title?.let { title ->
                songs.value.first { it.title == title }
            } ?: songs.value.first()
        }
        else{
            return Songs(title = "No Song selected")
        }
    }
    fun changeScreen(s: screenState) {
        _uiState.value = _uiState.value.copy(screenState = s)
    }
}