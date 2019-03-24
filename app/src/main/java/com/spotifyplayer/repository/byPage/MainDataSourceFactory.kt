package com.spotifyplayer.repository.byPage


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.spotifyplayer.api.RestRequest
import com.spotifyplayer.db.SpotifyDb
import com.spotifyplayer.models.Artist
import com.spotifyplayer.utils.AppExecuter


class MainDataSourceFactory(val tokenRequest    : RestRequest,
                            val query           : String,
                            val pageSize        : Int,
                            val database        : SpotifyDb,
                            val executer        : AppExecuter): DataSource.Factory<Int,Artist>() {

    val sourceLiveData  = MutableLiveData<MainPagedDataSource>()

    override fun create(): DataSource<Int, Artist> {
        val source = MainPagedDataSource(
            tokenRequest = tokenRequest,
            query        = query,
            executer     = executer,
            database     = database,
            pageSize     = pageSize)

        sourceLiveData.postValue(source)
        return source
    }
}