package com.spotifyplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spotifyplayer.R
import com.spotifyplayer.databinding.ItemVerticalRecyclerBinding
import com.spotifyplayer.models.Album
import com.spotifyplayer.models.Playlist
import com.spotifyplayer.models.Track
import com.spotifyplayer.models.TypeResult
import com.spotifyplayer.utils.artistCallBack
import java.util.*

class SearchVerticalAdapter : BaseAdapter() {

    var items = ArrayList<TypeResult<*>>()

    var artistCallBack : artistCallBack? = null

    fun submitItem(values : List<TypeResult<*>>){
        items.clear()
        items.addAll(values)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater        = LayoutInflater.from(parent.context)
        val item  = ItemVerticalRecyclerBinding.inflate(inflater,parent,false)
        return HorizontalHolder(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HorizontalHolder).bindData(items[position])
    }

    class HorizontalHolder(private val viewItem : ItemVerticalRecyclerBinding) : RecyclerView.ViewHolder(viewItem.root){

        fun bindData(item : TypeResult<*>){
            var resource : Int = 0
            if (item.items.isNotEmpty()){
                when(item.items[0]){
                    is Album -> {
                        resource = R.string.album
                    }
                    is Track -> {
                        resource = R.string.track
                    }
                    is Playlist -> {
                        resource = R.string.playlist
                    }
                    else -> {
                        resource = R.string.artist
                    }
                }
            }
            viewItem.title = viewItem.root.context.getString(resource)
            val adapter = SearchHorizontalAdapter(item)
            viewItem.horizontalRecycler.adapter        = adapter
        }
    }


}