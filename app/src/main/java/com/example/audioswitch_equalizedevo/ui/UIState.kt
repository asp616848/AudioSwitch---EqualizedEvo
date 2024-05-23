package com.example.audioswitch_equalizedevo.ui

data class UIState(val screenState: screenState = com.example.audioswitch_equalizedevo.ui.screenState.HOME, val seekVal: Long = -1, val songId: Long = -1, val isPlaying: Boolean = false, val isRepeat: Boolean = false, val isExpanded: Boolean = false, val isPlayingFrom: String = ""){
}