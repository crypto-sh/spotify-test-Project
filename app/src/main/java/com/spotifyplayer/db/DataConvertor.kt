package com.spotifyplayer.db

import androidx.room.TypeConverter
import com.bumptech.glide.load.ImageHeaderParser
import com.google.gson.Gson
import com.spotifyplayer.models.Image

class DataConvertor {

    @TypeConverter
    fun toString(images : List<Image>) : String{
        if (images.isNotEmpty()){
            return Gson().toJson(images)
        }
        return ""
    }

    @TypeConverter
    fun toImages(images : String) : List<Image> {
        val items = ArrayList<Image>()
        if (images.isNotEmpty()){
            val converted: List<Image> = Gson().fromJson(images, Array<Image>::class.java).toList()

            if (converted.isNotEmpty()){
                items.addAll(converted)
            }
        }
        return items
    }
}