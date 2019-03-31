package com.spotifyplayer.customViews


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.spotifyplayer.R
import com.spotifyplayer.enums.ShapeImage
import com.spotifyplayer.models.Image
import com.spotifyplayer.utils.GlideApp
import com.spotifyplayer.utils.GlideRequest
import com.spotifyplayer.utils.LogHelper

class ImageViewCustom : AppCompatImageView {

    private var radius: Float = 10F

    private var shape: ShapeImage = ShapeImage.UNDEFINE

    private val logHelper = LogHelper(this::class.java)

    constructor(context: Context?) : super(context)

    @SuppressLint("Recycle")
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val type = context!!.obtainStyledAttributes(attrs, R.styleable.ImageViewCustom)
        shape                = ShapeImage.parse(type.getInt(R.styleable.ImageViewCustom_image_shape, 0))
        radius               = type.getDimension(R.styleable.ImageViewCustom_radius, 0F)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        when (shape) {
            ShapeImage.ROUNDED,
            ShapeImage.SQUARE -> {
                super.onMeasure(widthMeasureSpec, widthMeasureSpec)
            }
            else -> {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        }
    }

    fun setUrl(image: Image) {
        imageHandler(GlideApp
            .with(this.context)
            .load(image.url)
            .override(image.width,image.height),
            false)
    }

    fun setUrl(image: String) {
        imageHandler(GlideApp.with(this.context).load(image),false)
    }

    private fun imageHandler(request: GlideRequest<Drawable>, cache : Boolean) {
        try {
            var loader = request
                .fitCenter()
                .skipMemoryCache(true)
                .centerInside()

            if (cache){
                loader = loader.diskCacheStrategy(DiskCacheStrategy.ALL)
            }
            loader = when (shape){
                ShapeImage.CIRCLE -> {
                    loader
                        .optionalCircleCrop()
                        .placeholder(R.drawable.shadow_circle_layout)
                }
                ShapeImage.SQUARE -> {
                    loader.placeholder(R.drawable.place_holder)
                }
                else -> {
                    loader
                        .apply(RequestOptions().transform(RoundedCorners(convertDpToPixelsInt(radius))))
                        .placeholder(R.drawable.place_holder)
                }
            }
            loader.into(this)
        } catch (e : Exception) {
            logHelper.e(e)
        }
    }

    private fun convertDpToPixelsInt(dp : Float)  : Int{
        val resources = context.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,resources.displayMetrics).toInt()
    }
}

