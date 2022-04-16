package com.priambudi19.carifoto.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.priambudi19.carifoto.data.LocalDataSource
import com.priambudi19.carifoto.data.RemoteDataSource
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.paging.PhotosPagingSource
import com.priambudi19.carifoto.utils.Resource
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : MainRepository {
    companion object {
        val pagingConfig = PagingConfig(pageSize = 10, prefetchDistance = 20)
    }

    override suspend fun getRandomPhotos(): Resource<List<Photo>> {
        return remoteDataSource.getRandomPhotos()
    }

    override suspend fun getSearchPhoto(query: String, page: Int): Flow<PagingData<Photo>> {
        val data = Pager(
            config = pagingConfig,
            pagingSourceFactory = { PhotosPagingSource(query, remoteDataSource) }
        )
        return data.flow
    }

    override suspend fun getDetailPhoto(id: String): Resource<Photo> {
        return remoteDataSource.getDetailPhoto(id)
    }

    override fun getFavoritePhotos(): Flow<Resource<List<Photo>>> {
        return localDataSource.getFavoritePhotos()
    }

    override fun getFavoritePhotosById(id: String): Flow<List<Photo>> {
        return localDataSource.getFavoritePhotoById(id)
    }

    override suspend fun insertFavoritePhotos(photo: Photo): Resource<Photo> {
        return localDataSource.insertFavoritePhotos(photo)
    }

    override suspend fun deleteFavoritePhotos(photo: Photo): Resource<Photo> {
        return localDataSource.deleteFavoritePhotos(photo)
    }

    override suspend fun deleteBulkFavoritePhotos(vararg photos: Photo): Resource<Photo> {
        return localDataSource.deleteBulkFavoritePhotos(*photos)
    }

}