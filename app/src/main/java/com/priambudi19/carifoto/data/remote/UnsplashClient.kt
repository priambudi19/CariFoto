package com.priambudi19.carifoto.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.priambudi19.carifoto.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object UnsplashClient {
    const val BASE_URL = "https://api.unsplash.com"
    private val gson: Gson = GsonBuilder().setLenient().create()
    private val logging = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) Level.BODY else Level.NONE
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val cr = chain.request()
            val new = cr.newBuilder()
                .addHeader("Authorization", BuildConfig.UNSPLASH_ACCESS_KEY)
                .method(cr.method, cr.body)
                .build()
            chain.proceed(new)
        }
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .build()


    fun getInstance(): UnsplashService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        return retrofit.create(UnsplashService::class.java)
    }
}