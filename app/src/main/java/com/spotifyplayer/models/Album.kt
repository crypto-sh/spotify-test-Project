@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.spotifyplayer.models

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import android.text.TextUtils
import android.R.id
import androidx.annotation.NonNull


data class Album (
    val id                  : String,
    val album_type          : String,
    val artists             : List<Artist>,
    val available_markets   : List<String>,
    val href                : String,
    val images              : List<Image>,
    val name                : String,
    val release_date        : String,
    val release_date_precision : String,
    val total_tracks        : Int,
    val type                : String,
    val uri                 : String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Artist),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.createTypedArrayList(Image),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    companion object CREATOR : Parcelable.Creator<Album> {

        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }

        // DiffCallback to assist Adapter
        val DIFF_CALLBACK : DiffUtil.ItemCallback<Album> = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem.equals(newItem)
            }

            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
                return TextUtils.equals(oldItem.id, newItem.id) && TextUtils.equals(oldItem.name, newItem.name)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == this){
            return true
        }
        val album = other as Album
        return  album.id == this.id
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(album_type)
        parcel.writeTypedList(artists)
        parcel.writeStringList(available_markets)
        parcel.writeString(href)
        parcel.writeTypedList(images)
        parcel.writeString(name)
        parcel.writeString(release_date)
        parcel.writeString(release_date_precision)
        parcel.writeInt(total_tracks)
        parcel.writeString(type)
        parcel.writeString(uri)
    }

    override fun describeContents(): Int {
        return 0
    }



}