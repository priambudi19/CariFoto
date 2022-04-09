package com.priambudi19.carifoto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.utils.DataConverter

@Database(entities = [Photo::class], exportSchema = false, version = 1)
@TypeConverters(DataConverter::class)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun getDao(): PhotosDao
}