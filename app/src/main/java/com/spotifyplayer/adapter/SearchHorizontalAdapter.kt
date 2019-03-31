package com.spotifyplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spotifyplayer.databinding.ItemAlbumBinding
import com.spotifyplayer.databinding.ItemArtistBinding
import com.spotifyplayer.databinding.ItemPlaylistBinding
import com.spotifyplayer.databinding.ItemTrackBinding
import com.spotifyplayer.enums.RowType
import com.spotifyplayer.models.*

class SearchHorizontalAdapter<T> constructor(private val item: TypeResult<T>) : BaseAdapter() {

    override fun getItemViewType(position: Int): Int {
        val value = item.items[position]
        return when (value) {
            is Album -> {
                RowType.ALBUM.value
            }
            is Track -> {
                RowType.TRACK.value
            }
            is Playlist -> {
                RowType.PLAYLIST.value
            }
            else -> {
                RowType.ARTIST.value
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (RowType.parse(viewType)) {
            RowType.ALBUM -> {
                val item = ItemAlbumBinding.inflate(inflater, parent, false)
                AlbumHolder(item)
            }
            RowType.TRACK -> {
                val item = ItemTrackBinding.inflate(inflater,parent,false)
                TrackHolder(item)
            }
            RowType.PLAYLIST -> {
                val item = ItemPlaylistBinding.inflate(inflater,parent,false)
                PlayListHolder(item)
            }
            else -> {
                val item = ItemArtistBinding.inflate(inflater, parent, false)
                ArtistHolder(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return item.items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = RowType.parse(getItemViewType(position))
        when (type) {
            RowType.ALBUM -> {
                (holder as AlbumHolder).bindData(item.items[position] as Album)
            }
            RowType.TRACK -> {
                (holder as TrackHolder).bindData(item.items[position] as Track)
            }
            RowType.PLAYLIST -> {
                (holder as PlayListHolder).bindData(item.items[position] as Playlist)
            }
            else -> {
                (holder as ArtistHolder).bindData(item.items[position] as Artist)
            }
        }
    }

    class AlbumHolder(private val viewItem: ItemAlbumBinding) : RecyclerView.ViewHolder(viewItem.root) {

        fun bindData(item: Album) {
            viewItem.album = item
            if (item.images.isNotEmpty()){
                viewItem.image = item.images[0].url
            }else{
                viewItem.image = ""
            }
        }
    }

    class ArtistHolder(private val viewItem: ItemArtistBinding) : RecyclerView.ViewHolder(viewItem.root) {

        fun bindData(item: Artist) {
            viewItem.artist = item
            if (item.images.isNotEmpty()){
                viewItem.image = item.images[0].url
            }else{
                viewItem.image = ""
            }
        }
    }


    class PlayListHolder(private val viewItem: ItemPlaylistBinding) : RecyclerView.ViewHolder(viewItem.root) {

        fun bindData(item: Playlist) {
            viewItem.playlist = item
            if (item.images.isNotEmpty()){
                viewItem.image = item.images[0].url
            }else{
                viewItem.image = ""
            }
        }
    }

    class TrackHolder(private val viewItem: ItemTrackBinding) : RecyclerView.ViewHolder(viewItem.root) {

        fun bindData(item: Track) {
            viewItem.track = item
            if (item.album.images.isNotEmpty()){
                viewItem.image = item.album.images[0].url
            }else{
                viewItem.image = ""
            }
        }
    }

}