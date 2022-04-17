package com.priambudi19.carifoto.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.data.repository.MainRepository
import com.priambudi19.carifoto.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
     suspend fun getListFavorite() = repository.getFavoritePhotos().asLiveData()
}
