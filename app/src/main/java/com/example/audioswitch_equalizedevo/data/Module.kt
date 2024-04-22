package com.example.audioswitch_equalizedevo.data

import FetchMusic
import android.content.Context
import com.example.audioswitch_equalizedevo.App
import com.example.audioswitch_equalizedevo.ui.viewModels.SongsViewModel
import com.google.android.datatransport.runtime.dagger.Module
import com.google.android.datatransport.runtime.dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(App::class)
object MyModule {
    @Provides
    fun provideFetchMusic(@ActivityContext context: Context): FetchMusic {
        return FetchMusic(context)
    }

    @Provides
    fun provideExoPlayer1(@ActivityContext context: Context): ExoPlayer1 {
        return ExoPlayer1(context)
    }
}
