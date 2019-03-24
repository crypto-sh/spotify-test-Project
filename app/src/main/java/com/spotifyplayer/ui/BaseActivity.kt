package com.spotifyplayer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spotifyplayer.api.RestApiFactory
import com.spotifyplayer.utils.AppExecuter
import com.spotifyplayer.utils.BaseApp
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton


abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var appExecuter: AppExecuter

    @Inject
    lateinit var tokenResquest : RestApiFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = this@BaseActivity.application as BaseApp
        //DaggerAppComponent.create().inject(app)



    }
}

