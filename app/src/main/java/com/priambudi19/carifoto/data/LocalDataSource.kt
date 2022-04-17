package com.priambudi19.carifoto.data

import com.priambudi19.carifoto.data.local.PhotosDao
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.utils.Resource
import okio.IOException

class LocalDataSource(private val dao: PhotosDao) {
    suspend fun getFavoritePhotos() = dao.getFavoritePhotos()

    fun getFavoritePhotoById(id: String) = dao.getDetailPhoto(id)

    suspend fun insertFavoritePhotos(photo: Photo): Resource<Photo> {
        return if (dao.insertFavoritePhotos(photo) > 0) {
            Resource.Success(photo)
        } else {
            val e = IOException("Error insert delete to favorite")
            Resource.Error(e, e.message!!)
        }
    }

    suspend fun deleteFavoritePhotos(photo: Photo): Resource<Photo> {
        return if (dao.deleteBulkFavoritePhotos(photo) > 0) {
            Resource.Success(photo)
        } else {
            val e = IOException("Error insert delete to favorite")
            Resource.Error(e, e.message!!)
        }
    }

    suspend fun deleteBulkFavoritePhotos(vararg photos: Photo): Resource<Photo> {
        return if (dao.deleteBulkFavoritePhotos(*photos) > 0) {
            Resource.Success(photos[0])
        } else {
            val e = IOException("Error insert delete to favorite")
            Resource.Error(e, e.message!!)
        }
    }
}