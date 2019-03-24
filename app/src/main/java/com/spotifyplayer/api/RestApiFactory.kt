package com.spotifyplayer.api

import android.text.TextUtils
import com.spotifyplayer.utils.BaseApp
import dagger.Module
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


@Module
class RestApiFactory @Inject constructor(app: BaseApp) {

    var restRequest : RestRequest? = null

    init {
        restRequest = createService(RestRequest::class.java, app.clientId(), app.clientSecret())
    }

    companion object {

        private fun <S> createService(serviceClass: Class<S>): S {
            return createService(serviceClass, null, null)
        }

        private fun <S> createService(serviceClass: Class<S>, username: String?, password: String?): S {
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                val authToken = Credentials.basic(username!!, password!!)
                return createService(serviceClass, authToken)
            }
            return createService(serviceClass, null)
        }

        private fun <S> createService(serviceClass: Class<S>, authToken: String?): S {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://accounts.spotify.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
            if (!TextUtils.isEmpty(authToken)) {
                val interceptor = AuthenticationInterceptor(authToken!!)
                val httpClient = OkHttpClient.Builder()
                if (!httpClient.interceptors().contains(interceptor)) {
                    httpClient.addInterceptor(interceptor)
                    retrofit.client(httpClient.build())
                }
            }
            return retrofit.build().create(serviceClass)
        }

        fun create(clientId : String, clientSecret : String) : RestRequest {
            return createService(RestRequest::class.java, clientId, clientSecret)
        }
    }
}