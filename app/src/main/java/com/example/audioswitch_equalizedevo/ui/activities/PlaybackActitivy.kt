package com.example.audioswitch_equalizedevo.ui.activities

import androidx.activity.ComponentActivity
import androidx.media3.exoplayer.ExoPlayer
import dagger.Component

class PlaybackActitivy : ComponentActivity(){
    val player = ExoPlayer.Builder(this).build()
    
}
