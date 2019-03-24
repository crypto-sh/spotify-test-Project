package com.spotifyplayer.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.github.library.RestClient
import com.spotifyplayer.models.Album
import com.spotifyplayer.models.NetworkState
import com.spotifyplayer.utils.AppExecuter
import com.spotifyplayer.utils.LogHelper

class Datarepository
    (val restApi    : RestClient,
     val query      : String,
     val executer   : AppExecuter
) {

    val helper          = LogHelper(this::class.java)
    val networkState    = MutableLiveData<NetworkState>()
    val data            = MutableLiveData<PagedList<Album>>()


}