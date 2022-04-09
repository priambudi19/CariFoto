package com.priambudi19.carifoto.data.remote

import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UnsplashService {
    @GET("/search/photos")
    suspend fun getSearch(): SearchResponse

    @GET("/photos/random")
    suspend fun getRandom(): List<Photo>

    @GET("/photos/{id}")
    suspend fun getDetail(@Path("id") id: String): Photo
}