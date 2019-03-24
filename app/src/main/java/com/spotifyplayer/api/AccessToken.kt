package com.spotifyplayer.api

class AccessToken {

    val access_token = String()
    var token_type   = String()
    var expires_in   : Int = 0
    var scope        = String()
}