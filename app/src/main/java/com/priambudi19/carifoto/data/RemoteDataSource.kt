package com.priambudi19.carifoto.data

import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.data.remote.UnsplashService
import com.priambudi19.carifoto.utils.Resource

class RemoteDataSource(private val apiService: UnsplashService) {
    suspend fun getRandomPhotos(): Resource<List<Photo>>{
        return try {
            val data = apiService.getRandom()
            Resource.Success(data)
        }catch (e : Exception){
            Resource.Error(e,e.message!!)
        }
    }
    suspend fun getDetailPhoto(id: String): Resource<Photo>{
        return try {
            val data = apiService.getDetail(id)
            Resource.Success(data)
        }catch (e : Exception){
            Resource.Error(e,e.message!!)
        }
    }
    suspend fun getSearchPhoto(query: String,page:Int): Resource<List<Photo>>{
        return try {
            val data = apiService.getSearch(query, page)
            Resource.Success(data.results)
        }catch (e : Exception){
            Resource.Error(e,e.message!!)
        }
    }
}