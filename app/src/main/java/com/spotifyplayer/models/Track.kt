@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.spotifyplayer.models

import android.os.Parcel
import android.os.Parcelable


data class Track(
    val album               : Album,
    val artists             : List<Artist>,
    val available_markets   : List<String>,
    val disc_number         : Int,
    val duration_ms         : Long,
    val explicit            : Boolean,
    val href                : String,
    val id                  : String,
    val is_local            : Boolean,
    val name                : String,
    val popularity          : Int,
    val track_number        : Int,
    val uri                 : String
    ) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Album::class.java.classLoader),
        parcel.createTypedArrayList(Artist),
        parcel.createStringArrayList(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(album, flags)
        parcel.writeTypedList(artists)
        parcel.writeStringList(available_markets)
        parcel.writeInt(disc_number)
        parcel.writeLong(duration_ms)
        parcel.writeByte(if (explicit) 1 else 0)
        parcel.writeString(href)
        parcel.writeString(id)
        parcel.writeByte(if (is_local) 1 else 0)
        parcel.writeString(name)
        parcel.writeInt(popularity)
        parcel.writeInt(track_number)
        parcel.writeString(uri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Track> {
        override fun createFromParcel(parcel: Parcel): Track {
            return Track(parcel)
        }

        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }
    }
}
