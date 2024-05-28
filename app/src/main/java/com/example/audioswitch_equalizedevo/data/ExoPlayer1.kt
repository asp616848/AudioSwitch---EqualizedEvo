package com.example.audioswitch_equalizedevo.data

import android.content.Context
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ExoPlayer1 @Inject constructor(@ActivityContext val context: Context) {
    private val exoPlayer = ExoPlayer.Builder(context).build()
    var MediaList: List<MediaItem> = emptyList()

    fun getExoPlayer(): ExoPlayer {
        return exoPlayer
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
}