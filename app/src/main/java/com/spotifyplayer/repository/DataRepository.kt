package com.spotifyplayer.repository

import androidx.lifecycle.MutableLiveData
import com.spotifyplayer.api.RestRequest
import com.spotifyplayer.api.RestRequest.Companion.executer
import com.spotifyplayer.enums.NetworkState
import com.spotifyplayer.models.TypeResult
import com.spotifyplayer.models.webResult
import com.spotifyplayer.utils.AppExecuter
import com.spotifyplayer.utils.LogHelper
import com.spotifyplayer.utils.OnAuthorization
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DataRepository(
    private val tokenRequest: RestRequest,
    private val query       : String,
    private val isTestMode  : Boolean,
                offset      : Int,
                limit       : Int,
    private val executor    : AppExecuter
) {

    val helper = LogHelper(this::class.java)
    val networkState = MutableLiveData<NetworkState>()
    val data = MutableLiveData<List<TypeResult<*>>>()

    init {
        getData(offset, limit)
    }

    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            executor.networkIO.execute {
                it.invoke()
            }
        }
    }

    fun getData(offset: Int, limit: Int) {
        networkState.postValue(NetworkState.LOADING)
        executer(executor.networkIO, isTestMode, tokenRequest, object : OnAuthorization {
            override fun onAuthorized(restApi: RestRequest) {
                restApi.searchArtist("name=$query", "album,artist,playlist,track", offset, limit)
                    .enqueue(object : Callback<webResult> {

                        override fun onFailure(call: Call<webResult>, t: Throwable) {
                            networkState.postValue(NetworkState.error(t.message))
                            retry = {
                                getData(offset, limit)
                            }
                        }

                        override fun onResponse(call: Call<webResult>, response: Response<webResult>) {
                            try {
                                if (response.isSuccessful) {
                                    retry = null
                                    val result = response.body()
                                    val itemsResult = ArrayList<TypeResult<*>>()

                                    if (result!!.albums.items.isNotEmpty())
                                        itemsResult.add(result.albums)

                                    if (result.artists.items.isNotEmpty())
                                        itemsResult.add(result.artists)

                                    if (result.playlists.items.isNotEmpty())
                                        itemsResult.add(result.playlists)

                                    if (result.tracks.items.isNotEmpty())
                                        itemsResult.add(result.tracks)
                                    data.postValue(itemsResult.toList())
                                    networkState.postValue(NetworkState.LOADED)
                                } else {
                                    networkState.postValue(NetworkState.error("error code:  ${response.code()}"))
                                }
                            } catch (e: Exception) {
                                networkState.postValue(NetworkState.error(e.message ?: "unknown error"))
                                retry = {
                                    getData(offset, limit)
                                }
                            }
                        }
                    })
            }

            override fun onFaild(errorMessage: String) {
                networkState.postValue(NetworkState.error(errorMessage))
                retry = {
                    getData(offset, limit)
                }
            }
        })
    }
}