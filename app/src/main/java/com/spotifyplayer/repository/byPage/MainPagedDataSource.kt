package com.spotifyplayer.repository.byPage

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.spotifyplayer.api.RestRequest

import com.spotifyplayer.db.SpotifyDb
import com.spotifyplayer.models.Artist
import com.spotifyplayer.enums.NetworkState
import com.spotifyplayer.models.webResult
import com.spotifyplayer.utils.AppExecuter
import com.spotifyplayer.utils.LogHelper
import com.spotifyplayer.utils.OnAuthorization
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPagedDataSource(
    val tokenRequest    : RestRequest,
    val query           : String,
    val pageSize        : Int,
    val isTestMode      : Boolean,
    val database        : SpotifyDb,
    val executer        : AppExecuter
) : PageKeyedDataSource<Int, Artist>() {

    private var retry: (() -> Any)? = null

    private val logHelper = LogHelper(this::class.java)

    val networkState = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            executer.networkIO.execute {
                it.invoke()
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Artist>) {
        networkState.postValue(NetworkState.LOADING)
        RestRequest.executer(executer.networkIO, isTestMode,tokenRequest, object : OnAuthorization {
            override fun onAuthorized(restApi: RestRequest) {
                restApi.searchArtist(query, "album,artist,playlist,track", 0, pageSize)
                    .enqueue(object : Callback<webResult> {

                        override fun onFailure(call: Call<webResult>, t: Throwable) {
                            networkState.postValue(NetworkState.error(t.message))
                            retry = {
                                loadInitial(params, callback)
                            }
                        }

                        override fun onResponse(call: Call<webResult>, response: Response<webResult>) {
                            try {
                                if (response.isSuccessful) {
                                    retry = null
                                    networkState.postValue(NetworkState.LOADED)
                                    val result = response.body()
                                    //TODO insert data into database if database needed

                                    callback.onResult(result!!.artists.items, null, 2)
                                } else {
                                    networkState.postValue(NetworkState.error("error code:  ${response.code()}"))
                                }
                            } catch (e: Exception) {
                                networkState.postValue(NetworkState.error(e.message ?: "unknown error"))
                                retry = {
                                    loadInitial(params, callback)
                                }
                            }
                        }
                    })
            }

            override fun onFaild(errorMessage: String) {
                networkState.postValue(NetworkState.error(errorMessage))
                retry = {
                    loadInitial(params, callback)
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Artist>) {
        networkState.postValue(NetworkState.LOADING)
        RestRequest.executer(executer.networkIO, isTestMode,tokenRequest, object : OnAuthorization {
            override fun onAuthorized(restApi: RestRequest) {
                restApi
                    .searchArtist(query, "album", 0, pageSize)
                    .enqueue(object : Callback<webResult> {

                        override fun onFailure(call: Call<webResult>, t: Throwable) {
                            networkState.postValue(NetworkState.error(t.message))
                            retry = {
                                loadAfter(params, callback)
                            }
                        }

                        override fun onResponse(call: Call<webResult>, response: Response<webResult>) {
                            try {
                                if (response.isSuccessful) {
                                    retry = null
                                    networkState.postValue(NetworkState.LOADED)
                                    val items = response.body()?.artists!!.items
                                    //TODO insert data into database if database needed

                                    callback.onResult(items, params.key + 1)
                                } else {
                                    networkState.postValue(NetworkState.error("error code:  ${response.code()}"))
                                }
                            } catch (e: Exception) {
                                networkState.postValue(NetworkState.error(e.message ?: "unknown error"))
                                retry = {
                                    loadAfter(params, callback)
                                }
                            }
                        }
                    })
            }

            override fun onFaild(errorMessage: String) {
                networkState.postValue(NetworkState.error(errorMessage))
                retry = {
                    loadAfter(params, callback)
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Artist>) {
        logHelper.d("loadBefore")
    }

}