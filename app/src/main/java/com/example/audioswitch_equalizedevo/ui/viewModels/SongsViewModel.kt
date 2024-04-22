package com.example.audioswitch_equalizedevo.ui.viewModels


import android.app.Application
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.audioswitch_equalizedevo.data.FetchMusic
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
class SongsViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {
    private val _songs = MutableStateFlow<List<Songs>>(emptyList())
    val songs: StateFlow<List<Songs>> = _songs
    init {
        fetchSongs()
    }

    val exoPlayer = ExoPlayer.Builder(context).build()
    var MediaList: List<MediaItem> = emptyList()

    @Inject lateinit var fetchMusic: FetchMusic

    fun fetchSongs() {
        fetchMusic.getPlayList().let {
            _songs.value = it
        }
    }

    private val _uiState = MutableStateFlow(UIState())
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()


    fun playPause() {
        if(_uiState.value.isPlaying){
            _uiState.value = _uiState.value.copy(isPlaying = false)
            exoPlayer.pause()
        }
        else{
            _uiState.value = _uiState.value.copy(isPlaying = true)
            for (song in songs.value){
                MediaList += MediaItem.fromUri (Uri.parse(song.fileUri))
            }
            exoPlayer.setMediaItems(MediaList)
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }
    fun getcurrentSong() : Songs {
        if(!songs.value.isEmpty()){
            return exoPlayer.currentMediaItem?.mediaMetadata?.title?.let { title ->
                songs.value.first { it.title == title }
            } ?: songs.value.first()
        }
        return Songs()
    }
    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }

    fun playNext() {
        if (exoPlayer.hasNextMediaItem()){
            exoPlayer.seekToNextMediaItem()
        }else{
            Toast.makeText(context, "No Next Song", Toast.LENGTH_SHORT).show()
        }
    }
    fun playPrev() {
        if (exoPlayer.hasPreviousMediaItem()){
            exoPlayer.seekToPreviousMediaItem()
        }else{
            Toast.makeText(context, "No Previous Song", Toast.LENGTH_SHORT).show()
        }
    }
    fun changeScreen(s: screenState) {
        _uiState.value = _uiState.value.copy(screenState = s)
    }
}