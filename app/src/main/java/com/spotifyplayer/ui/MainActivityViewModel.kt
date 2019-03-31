package com.spotifyplayer.ui

import androidx.annotation.MainThread
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.spotifyplayer.api.RestRequest
import com.spotifyplayer.db.SpotifyDb
import com.spotifyplayer.enums.NetworkState
import com.spotifyplayer.models.Artist
import com.spotifyplayer.models.RepositoryResultPaging
import com.spotifyplayer.repository.byPage.MainDataSourceFactory
import com.spotifyplayer.utils.AppExecuter


class MainActivityViewModel(
    val tokenRequest: RestRequest,
    val isTestMode: Boolean,
    val database: SpotifyDb,
    val executer: AppExecuter
) :
    ViewModel() {

    private val query = MutableLiveData<String>()

    private val repoResult = Transformations.map(query) {
        searchQueryData(query = it)
    }!!

    val data = Transformations.switchMap(repoResult) { it.pagedList }!!
    val networkState = Transformations.switchMap(repoResult) {
        it.networkState
    }!!

    val showingProgress = ObservableBoolean()

    fun searchResultShow(searchQuery: String) {
        query.value.let {
            if (it == searchQuery) {
                return
            } else {
                //for search query we should add name at fisrt
                query.postValue("name=$searchQuery")
            }
        }
    }

    fun retry() {
        val listing = repoResult.value
        listing?.retry?.invoke()
    }

    @MainThread
    fun searchQueryData(query: String): RepositoryResultPaging<Artist> {
        val pageSize = 20
        val sourceFactory = MainDataSourceFactory(
            tokenRequest = tokenRequest,
            query = query,
            isTestMode = isTestMode,
            executer = executer,
            database = database,
            pageSize = pageSize
        )

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(pageSize)
            .setPageSize(pageSize)
            .build()

        val livePagedList = LivePagedListBuilder<Int, Artist>(sourceFactory, pagedListConfig).build()

        return RepositoryResultPaging(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            })
    }

    fun showProgress(showing: NetworkState) {
        when (showing) {
            NetworkState.LOADING -> {
                showingProgress.set(true)
            }
            else -> {
                showingProgress.set(false)
            }
        }
    }

    class Factory(
        val tokenRequest: RestRequest,
        val isTestMode: Boolean,
        val database: SpotifyDb,
        val executer: AppExecuter
    ) :
        ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(
                tokenRequest = tokenRequest,
                database = database,
                isTestMode = isTestMode,
                executer = executer
            ) as T
        }
    }

}