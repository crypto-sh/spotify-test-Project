package com.spotifyplayer.adapter


import android.view.View
import androidx.databinding.BindingAdapter
import com.spotifyplayer.customViews.ImageViewCustom
import com.spotifyplayer.models.Image


class BindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("VisibleOrGone")
        fun View.setVisibility(visible : Boolean){
            this.visibility = if (visible) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun ImageViewCustom.showImages(image : Image){
            this.setUrl(image = image)
        }
    }


}