package com.priambudi19.carifoto.data.repository

import androidx.paging.PagingData
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getRandomPhotos(): Resource<List<Photo>>
    suspend fun getSearchPhoto(query: String, page: Int = 1): Flow<PagingData<Photo>>
    suspend fun getDetailPhoto(id: String): Resource<Photo>
    suspend fun getFavoritePhotos():Flow<List<Photo>>
    fun getFavoritePhotosById(id: String): Flow<List<Photo>>
    suspend fun insertFavoritePhotos(photo: Photo): Resource<Photo>
    suspend fun deleteFavoritePhotos(photo: Photo): Resource<Photo>
    suspend fun deleteBulkFavoritePhotos(vararg photos: Photo): Resource<Photo>
}