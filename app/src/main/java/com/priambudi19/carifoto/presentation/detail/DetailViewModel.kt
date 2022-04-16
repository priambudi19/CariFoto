package com.priambudi19.carifoto.presentation.detail

import android.content.Context
import androidx.lifecycle.*
import androidx.work.*
import com.priambudi19.carifoto.data.model.Photo
import com.priambudi19.carifoto.data.repository.MainRepository
import com.priambudi19.carifoto.download.FotoDownloadManager
import com.priambudi19.carifoto.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _detail: MutableLiveData<Resource<Photo>> = MutableLiveData()
    val detail: LiveData<Resource<Photo>> get() = _detail
    private var id: String = ""
    var isFavorite = false

    fun getDetail(id: String) {
        this.id = id
        viewModelScope.launch {
            _detail.postValue(Resource.Loading())
            val data = repository.getDetailPhoto(id)
            _detail.postValue(data)
        }
    }

    fun checkIsFavorite() = repository.getFavoritePhotosById(id).asLiveData()

    fun addToFavorite(photo: Photo) {
        viewModelScope.launch {
            repository.insertFavoritePhotos(photo)
        }
    }

    fun deleteFromFavorite(photo: Photo) {
        viewModelScope.launch {
            repository.deleteFavoritePhotos(photo)
        }
    }

    fun download(photo: Photo, context: Context): LiveData<WorkInfo> {
        val data = Data.Builder()
        data.apply {
            putString(FotoDownloadManager.FileParams.KEY_FILE_NAME, photo.id)
            putString(FotoDownloadManager.FileParams.KEY_FILE_URL, photo.urls.regular)
            putString(FotoDownloadManager.FileParams.KEY_FILE_TYPE, "image/jpeg")
        }

        val downloadWorkRequest = OneTimeWorkRequestBuilder<FotoDownloadManager>()
            .setInputData(data.build())
            .build()
       val work =  WorkManager.getInstance(context)
        work.enqueueUniqueWork(
            "download_foto_${System.currentTimeMillis()}",
            ExistingWorkPolicy.KEEP,
            downloadWorkRequest
        )
        return work.getWorkInfoByIdLiveData(downloadWorkRequest.id)

    }


}