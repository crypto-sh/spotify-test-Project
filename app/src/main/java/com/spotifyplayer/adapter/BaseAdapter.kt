package com.spotifyplayer.adapter

import androidx.recyclerview.widget.RecyclerView
import com.spotifyplayer.enums.NetworkState

abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var networkState: NetworkState? = null

    fun hasExtraRow(): Boolean {
        return networkState != null && networkState == NetworkState.LOADED
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }
}