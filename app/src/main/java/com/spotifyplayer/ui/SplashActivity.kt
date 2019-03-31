package com.spotifyplayer.ui

import android.os.Bundle
import com.spotifyplayer.R
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashImage.setUrl(getString(R.string.splash_image))


    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main) { // launch coroutine in the main thread
            delay(1000)
            show()
        }
    }

    fun show(){
        val intent = ListWithoutPaging.intentFor(this,false)
        startActivity(intent)
        finish()
    }
}
