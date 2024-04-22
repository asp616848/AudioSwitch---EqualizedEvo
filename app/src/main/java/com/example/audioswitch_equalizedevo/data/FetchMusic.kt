package com.example.audioswitch_equalizedevo.data

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class FetchMusic @Inject constructor(@ActivityContext val context: Context){

    private val songList = ArrayList<Songs>()
    private val albumArtUri: Uri = Uri.parse("content://media/external/audio/albumart")

    fun getPlayList() : ArrayList<Songs> {
        val selection: String? = null
        val selectionArgs: Array<String>? = null

        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID
        )

        val audioCursor: Cursor? = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            proj,
            selection,
            selectionArgs,
            null
        )

        audioCursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val audioTitle = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    val audioArtist = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                    val audioDuration = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                    val audioData = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    val audioAlbum = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                    val audioAlbumId = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                    val songId = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)

                    val info = Songs().apply {
                        fileUri = cursor.getString(audioData)
                        title = cursor.getString(audioTitle)
                        duration = cursor.getString(audioDuration)
                        artist = cursor.getString(audioArtist)
                        album = cursor.getString(audioAlbum)
                        id = cursor.getLong(songId)
                        albumArt = ContentUris.withAppendedId(albumArtUri, cursor.getLong(audioAlbumId)).toString()
                    }
                    songList.add(info)
                } while (cursor.moveToNext())
            }
        }
        return songList
    }
}
