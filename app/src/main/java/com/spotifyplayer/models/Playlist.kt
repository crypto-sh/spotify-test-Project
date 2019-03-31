package com.spotifyplayer.models



data class Playlist(
    val collaborative   : Boolean,
    val href            : String,
    val id              : String,
    val images          : List<Image>,
    val name            : String,
    val snapshot_id     : String,
    val type            : String,
    val uri             : String) {

}