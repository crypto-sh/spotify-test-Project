package com.spotifyplayer.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(val authToken : String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original        = chain.request()
        var builder     = original.newBuilder().header("Authorization", authToken)
        val request = builder.build()
        return chain.proceed(request)
    }


}