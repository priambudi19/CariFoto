package com.priambudi19.carifoto.di

import android.content.Context
import androidx.room.Room
import com.priambudi19.carifoto.data.LocalDataSource
import com.priambudi19.carifoto.data.RemoteDataSource
import com.priambudi19.carifoto.data.local.PhotosDao
import com.priambudi19.carifoto.data.local.PhotosDatabase
import com.priambudi19.carifoto.data.remote.UnsplashClient
import com.priambudi19.carifoto.data.remote.UnsplashService
import com.priambudi19.carifoto.data.repository.MainRepository
import com.priambudi19.carifoto.data.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApiService(): UnsplashService = UnsplashClient.getInstance()

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: UnsplashService): RemoteDataSource =
        RemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PhotosDatabase {
        return Room.databaseBuilder(context, PhotosDatabase::class.java, "db_photo")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDao(db: PhotosDatabase): PhotosDao = db.getDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(photosDao: PhotosDao): LocalDataSource = LocalDataSource(photosDao)

    @Singleton
    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MainRepository {
        return MainRepositoryImpl(remoteDataSource, localDataSource)
    }
}