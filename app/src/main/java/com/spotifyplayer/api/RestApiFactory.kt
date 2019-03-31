package com.spotifyplayer.api

import android.text.TextUtils
import com.spotifyplayer.utils.BaseApp
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

var restRequest: RestRequest? = null

class RestApiFactory @Inject constructor() {


    companion object {

        private val BASE_URL_ACCESS_TOKEN = "https://accounts.spotify.com/api/"
        private val TEST_URL_ACCESS_TOKEN = "http://localhost:8080/"

        private fun BASE_URL(isTest: Boolean) = if (!isTest)
            BASE_URL_ACCESS_TOKEN
        else
            TEST_URL_ACCESS_TOKEN

        private fun <S> createService(serviceClass: Class<S>, isTest: Boolean): S {
            return createService(serviceClass, isTest, null)
        }

        private fun <S> createService(
            serviceClass: Class<S>,
            isTest: Boolean,
            username: String?,
            password: String?
        ): S {
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                val authToken = Credentials.basic(username!!, password!!)
                return createService(serviceClass, isTest, authToken)
            }
            return createService(serviceClass, isTest, null)
        }

        private fun <S> createService(serviceClass: Class<S>, isTest: Boolean, authToken: String?): S {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL(isTest = isTest))
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

        fun create(app: BaseApp, isTest: Boolean): RestRequest {
            if (restRequest == null) {
                restRequest = createService(RestRequest::class.java, isTest, app.clientId(), app.clientSecret())
            }
            return restRequest!!
        }
    }


}