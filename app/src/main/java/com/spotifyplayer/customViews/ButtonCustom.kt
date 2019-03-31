package com.spotifyplayer.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class ButtonCustom : AppCompatButton {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

}