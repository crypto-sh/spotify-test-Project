package com.spotifyplayer.adapter


import android.view.View
import androidx.databinding.BindingAdapter
import com.spotifyplayer.customViews.ImageViewCustom


class BindingAdapter {

    companion object {

        @BindingAdapter("VisibleOrGone")
        @JvmStatic fun setVisibility(view: View, status : Boolean){
            if (status) view.visibility = View.VISIBLE else view.visibility = View.GONE
        }

        @BindingAdapter("imageUrl")
        @JvmStatic fun showImages(imageView : ImageViewCustom, image : String){
            imageView.setUrl(image = image)
        }
    }


}