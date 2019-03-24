package com.spotifyplayer.api

import com.spotifyplayer.models.webResult
import com.spotifyplayer.utils.LogHelper
import com.spotifyplayer.utils.OnAuthorization
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.Executor


interface RestRequest {

    @FormUrlEncoded
    @POST("token")
    fun getAccessToken(@Field("grant_type") grantType: String): Call<AccessToken>


    @GET("search")//type=album&offset=$offset&limit=$limit
    fun searchArtist(@Query("q") album : String,
                     @Query("type") type : String,
                     @Query("offset") offset : Int,
                     @Query("limit") limit : Int): Call<webResult>

    companion object {

        private const val BASE_URL = "https://api.spotify.com/v1/"

        val logHelper = LogHelper(this::class.java)

        fun executer(networkExecutor: Executor, loginService: RestRequest, callback: OnAuthorization) {
            try {
                networkExecutor.execute {
                    val call = loginService.getAccessToken("client_credentials")
                    call.enqueue(object : Callback<AccessToken>{
                        override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                            callback.onFaild(t.message!!)
                        }

                        override fun onResponse(call: Call<AccessToken>, response: retrofit2.Response<AccessToken>) {
                            callback.onAuthorized(getRestApi(response.body()!!))
                        }
                    })
                }
            } catch (e: Exception) {
                logHelper.e(e)
            }
        }

        private fun getRestApi(token: AccessToken): RestRequest {
            val httpClient = OkHttpClient.Builder()
            val interceptor = object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request     = chain.request().newBuilder()
                        .header("Authorization" , "Bearer ${token.access_token}")
                        .header("Accept"        ," application/json")
                        .build()
                    return chain.proceed(request)
                }
            }
            httpClient.addInterceptor(interceptor)
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
            return retrofit.create(RestRequest::class.java)
        }
    }

}