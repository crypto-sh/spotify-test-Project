package com.spotifyplayer.utils

import android.app.Application


class BaseApp : Application() {

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    external fun clientId(): String

    external fun clientSecret(): String


}