package com.spotifyplayer.ui


import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spotifyplayer.api.RestRequest
import com.spotifyplayer.enums.NetworkState
import com.spotifyplayer.models.RepositoryResult
import com.spotifyplayer.models.TypeResult
import com.spotifyplayer.repository.DataRepository
import com.spotifyplayer.utils.AppExecuter


class ListWithoutPagingViewModel(
    val tokenRequest: RestRequest,
    val isTestMode: Boolean,
    val executor: AppExecuter
) : ViewModel() {

    var query = MutableLiveData<String>()

    private var dataRepository: DataRepository? = null

    private val repoResult = Transformations.map(query) {
        searchQueryData(query = it)
    }!!
    val data = Transformations.switchMap(repoResult) {
        it.livedataList
    }!!
    val networkState = Transformations.switchMap(repoResult) {
        it.networkState
    }!!

    val showingProgress = ObservableBoolean()

    fun searchQueryData(query: String): RepositoryResult<TypeResult<*>> {
        val pageSize = 20
        dataRepository = DataRepository(
            tokenRequest    = tokenRequest,
            query           = query,
            isTestMode      = isTestMode,
            offset          = 0,
            limit           = pageSize,
            executor        = executor
        )

        return RepositoryResult(dataRepository!!.data, dataRepository!!.networkState)
    }

    fun retry() {
        dataRepository!!.retryAllFailed()
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
        val executor: AppExecuter
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return ListWithoutPagingViewModel(tokenRequest = tokenRequest, isTestMode = isTestMode, executor = executor) as T
        }
    }

}