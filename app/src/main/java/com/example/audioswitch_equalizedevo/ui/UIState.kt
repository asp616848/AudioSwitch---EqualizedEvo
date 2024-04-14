package com.example.audioswitch_equalizedevo.ui

data class UIState( val screenState: screenState = com.example.audioswitch_equalizedevo.ui.screenState.HOME) {
    var isPlaying: Boolean = false;
}