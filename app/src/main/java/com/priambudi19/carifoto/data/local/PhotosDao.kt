package com.priambudi19.carifoto.data.local

import androidx.room.*
import com.priambudi19.carifoto.data.model.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {
    @Query("select * from tb_photos")
    fun getFavoritePhotos(): Flow<List<Photo>>

    @Query("select * from tb_photos where id=:id limit 1")
    fun getDetailPhoto(id: String) : Flow<List<Photo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePhotos(photo: Photo) : Long

    @Delete
    suspend fun deleteFavoritePhotos(photo: Photo) : Int

    @Delete
    suspend fun deleteBulkFavoritePhotos(vararg photos: Photo) : Int
}
