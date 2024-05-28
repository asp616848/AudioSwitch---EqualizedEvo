package com.example.audioswitch_equalizedevo.ui

import android.net.Uri
import com.example.audioswitch_equalizedevo.data.Songs

data class UIState(val screenState: screenState = com.example.audioswitch_equalizedevo.ui.screenState.HOME, val seekVal: Long = -1, val songId:String = "", val isPlaying: Boolean = false, val isRepeat: Boolean = false, val isExpanded: Boolean = false, val isPlayingFrom: String = ""){
}