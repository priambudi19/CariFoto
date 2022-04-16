package com.priambudi19.carifoto.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.priambudi19.carifoto.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel(){
    val getListFavorite = repository.getFavoritePhotos().asLiveData()
}
