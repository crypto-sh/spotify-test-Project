package com.spotifyplayer.utils

import android.os.Handler
import android.os.Looper
import dagger.Module
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

var INSTANCE : AppExecuter? = null

@Module class AppExecuter constructor(var diskIO: Executor, var networkIO: Executor, var mainThread: Executor) {

    companion object {

        fun getAppExecutor() : AppExecuter {
            if (INSTANCE == null){
                INSTANCE = AppExecuter()
            }
            return INSTANCE!!
        }
    }

    @Inject constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecuter()
    )

    class MainThreadExecuter : Executor {
        var handler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            handler.post(command)
        }

    }
}