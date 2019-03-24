package com.spotifyplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.spotifyplayer.databinding.ItemArtistBinding
import com.spotifyplayer.models.Artist
import com.spotifyplayer.models.NetworkState
import com.spotifyplayer.utils.artistCallBack

class SearchPagedAdapter : PagedListAdapter<Artist, RecyclerView.ViewHolder>(Artist.DIFF_CALLBACK) {

    var items = ArrayList<Artist>()

    var artistCallBack : artistCallBack? = null

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater    = LayoutInflater.from(parent.context)
        val itemArtist = ItemArtistBinding.inflate(inflater, parent,false)
        return ArtistHolder(itemArtist)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArtistHolder).bindData(items[position])
    }

    class ArtistHolder(private val viewItem : ItemArtistBinding) : RecyclerView.ViewHolder(viewItem.root){

        fun bindData(artist : Artist){
            viewItem.artist = artist
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState !== NetworkState.LOADED
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