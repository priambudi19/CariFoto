package com.priambudi19.carifoto.presentation.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.priambudi19.carifoto.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    var query: String = ""

    suspend fun getResult(query: String) = repository.getSearchPhoto(query).asLiveData()
}
