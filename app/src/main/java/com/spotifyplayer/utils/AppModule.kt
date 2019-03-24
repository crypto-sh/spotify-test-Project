package com.spotifyplayer.utils

import com.spotifyplayer.api.RestApiFactory
import com.spotifyplayer.api.RestRequest
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    fun inject(app: BaseApp)
}

@Module
class AppModule constructor(){


}