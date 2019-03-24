package com.spotifyplayer.ui

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.spotifyplayer.R
import com.spotifyplayer.databinding.ActivitySplashBinding
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    var binding : ActivitySplashBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)
        splashImage.setUrl(getString(R.string.splash_image))
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch(Dispatchers.Main) { // launch coroutine in the main thread
            delay(2000)
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            finish()
        }
    }
}
