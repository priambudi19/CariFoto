package com.priambudi19.carifoto.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.data.repository.MainRepository
import com.priambudi19.carifoto.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _randomPhotos: MutableLiveData<Resource<List<Photo>>> =
        MutableLiveData(Resource.Loading())
    val randomPhoto: LiveData<Resource<List<Photo>>> get() = _randomPhotos
    init {
        getRandomPhotos()
    }

    private fun getRandomPhotos() {
        viewModelScope.launch {
            _randomPhotos.postValue(Resource.Loading())
            when(val data = repository.getRandomPhotos()){
                is Resource.Success -> {
                    _randomPhotos.postValue(data)
                }
                is Resource.Error -> {
                    _randomPhotos.postValue(data)
                }
                else -> {}
            }
        }
    }

}