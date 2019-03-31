package com.spotifyplayer.models

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey val id: String,
    val name: String,
    val type: String,
    val uri: String,
    val href: String,
    val images: List<Image>,
    val popularity: Int,
    val spotify: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Image),
        parcel.readInt(),
        parcel.readString()
    ) {
    }


    override fun equals(other: Any?): Boolean {
        if (other == this) {
            return true
        }
        val artist = other as Artist
        return artist.id == this.id
    }
//
//    override fun hashCode(): Int {
//        var result = id.hashCode()
//        result = 31 * result + name.hashCode()
//        result = 31 * result + type.hashCode()
//        result = 31 * result + uri.hashCode()
//        result = 31 * result + href.hashCode()
//        result = 31 * result + spotify.hashCode()
//        return result
//    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(uri)
        parcel.writeString(href)
        parcel.writeTypedList(images)
        parcel.writeInt(popularity)
        parcel.writeString(spotify)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Artist> {
        override fun createFromParcel(parcel: Parcel): Artist {
            return Artist(parcel)
        }

        override fun newArray(size: Int): Array<Artist?> {
            return arrayOfNulls(size)
        }

        // DiffCallback to assist Adapter
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Artist> = object : DiffUtil.ItemCallback<Artist>() {
            override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                return oldItem.equals(newItem)
            }

            override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                return TextUtils.equals(oldItem.id, newItem.id) && TextUtils.equals(oldItem.name, newItem.name)
            }
        }
    }

}