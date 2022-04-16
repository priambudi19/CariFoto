package com.priambudi19.carifoto.data.remote

import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashService {
    @GET("/search/photos")
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): SearchResponse

    @GET("/photos/random?count=10")
    suspend fun getRandom(): ArrayList<Photo>

    @GET("/photos/{id}")
    suspend fun getDetail(@Path("id") id: String): Photo
}