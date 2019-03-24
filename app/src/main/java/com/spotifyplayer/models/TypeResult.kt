package com.spotifyplayer.models


data class TypeResult<T>(
    val href        : String,
    val limit       : Int,
    val next        : String,
    val offset      : Int,
    val previous    : String,
    val total       : Int,
    val items       : List<T>)