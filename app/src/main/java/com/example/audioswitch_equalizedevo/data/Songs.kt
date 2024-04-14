package com.example.audioswitch_equalizedevo.data

data class Songs(
    var id: Long = 0,
    var title: String = "",
    var artist: String= "",
    var album: String= "",
    var duration: String= "",
    var fileUri: String= "",
    var albumArt: String = ""
)
