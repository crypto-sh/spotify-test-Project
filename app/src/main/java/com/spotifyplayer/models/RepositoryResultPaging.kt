package com.spotifyplayer.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.spotifyplayer.enums.NetworkState

class RepositoryResultPaging<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val retry: () -> Unit
)


class RepositoryResult<T>(
    val livedataList: LiveData<List<T>>,
    val networkState: LiveData<NetworkState>
)