package com.priambudi19.carifoto.utils

sealed class Resource<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
    val msg: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(throwable: Throwable, msg: String) :
        Resource<T>(throwable = throwable, msg = msg)
    class Loading<T> : Resource<T>()
    class Empty<T> : Resource<T>()
}
