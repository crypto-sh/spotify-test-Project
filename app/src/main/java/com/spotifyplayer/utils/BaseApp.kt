package com.spotifyplayer.utils

import android.app.Application
import com.spotifyplayer.db.SpotifyDb


class BaseApp : Application() {

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun clientId(): String

    external fun clientSecret(): String

    var database : SpotifyDb? = null

    override fun onCreate() {
        super.onCreate()
        database = SpotifyDb.getInstance(applicationContext,false)
    }
}