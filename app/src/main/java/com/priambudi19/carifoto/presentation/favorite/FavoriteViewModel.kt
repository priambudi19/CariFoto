package com.priambudi19.carifoto.presentation.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.data.repository.MainRepository
import com.priambudi19.carifoto.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _listFavorite: MutableLiveData<Resource<List<Photo>>> =
        MutableLiveData(Resource.Loading())

    val getListFavorite get() = _listFavorite

    fun setData() = viewModelScope.launch {
        val data = withContext(Dispatchers.IO) { repository.getFavoritePhotos() }
        _listFavorite.postValue(data)
    }
}
