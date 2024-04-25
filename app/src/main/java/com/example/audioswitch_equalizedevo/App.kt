package com.example.audioswitch_equalizedevo

import android.app.Application
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}