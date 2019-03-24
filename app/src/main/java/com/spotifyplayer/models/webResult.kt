package com.spotifyplayer.models



data class webResult(
    val albums      : TypeResult<Album>,
    val artists     : TypeResult<Artist>,
    val playlists   : TypeResult<Playlist>,
    val tracks      : TypeResult<Track>){
}
