package com.priambudi19.carifoto.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ZoomViewModel : ViewModel() {
    private val _url = MutableLiveData("")
    val url : LiveData<String?> get() = _url
    
    fun setUrl(url:String?) = _url.postValue(url)
}
