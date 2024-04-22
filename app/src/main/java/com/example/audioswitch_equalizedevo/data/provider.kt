package com.example.audioswitch_equalizedevo.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ExoplayerModule {
    @Provides
    @Singleton
    fun provideExoplayer(@ApplicationContext context: Context?): ExoPlayer1 {
        // Initialize and configure your ExoPlayer1 instance here
        return ExoPlayer1(context!!) // Replace null with actual ExoPlayer1 instance
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object FetchMusicModule {

    @Provides
    @Singleton
    fun provideFetchMusic(@ApplicationContext context: Context): FetchMusic {
        // Initialize and configure your FetchMusic instance here
        return FetchMusic(context)
    }
}