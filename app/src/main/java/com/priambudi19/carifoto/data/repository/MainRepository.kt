package com.priambudi19.carifoto.data.repository

interface MainRepository {
    fun getRandomPhotos()
    fun getDetailPhoto()
    fun getSearchPhoto()
    fun getFavoritePhotos()
    fun insertFavoritePhotos()
    fun deleteFavoritePhotos()
}