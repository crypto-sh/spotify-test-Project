package com.spotifyplayer.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

class RepositoryResult<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<NetworkState>,
    val retry: () -> Unit
)