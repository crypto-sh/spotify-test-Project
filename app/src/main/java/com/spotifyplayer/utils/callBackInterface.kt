package com.spotifyplayer.utils

import com.spotifyplayer.api.RestRequest
import com.spotifyplayer.models.Artist


interface artistCallBack {
    fun onArtistSelected(artist : Artist)
}

interface OnAuthorization {
    fun onAuthorized(restApi: RestRequest)
    fun onFaild(errorMessage : String)
}