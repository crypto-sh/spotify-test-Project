package com.spotifyplayer.models


data class tracks(val href  : String,
                  val total : Int)

data class Playlist(
    val collaborative   : Boolean,
    val href            : String,
    val id              : String,
    val images          : List<Image>,
    val name            : String,
    val snapshot_id     : String,
    val tracks          : tracks,
    val type            : String,
    val uri             : String) {

}