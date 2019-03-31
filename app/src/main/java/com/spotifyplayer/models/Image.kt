@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.spotifyplayer.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.Gson

data class Image(val height     : Int,
                 val width      : Int,
                 val url        : String) : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    ) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(height)
        parcel.writeInt(width)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return Gson().toJson(Image).toString()
    }
}