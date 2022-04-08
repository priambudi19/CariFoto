package com.priambudi19.carifoto.model


import com.google.gson.annotations.SerializedName

data class Urls(
    @SerializedName("full")
    var full: String = "",
    @SerializedName("raw")
    var raw: String = "",
    @SerializedName("regular")
    var regular: String = "",
    @SerializedName("small")
    var small: String = "",
    @SerializedName("small_s3")
    var smallS3: String = "",
    @SerializedName("thumb")
    var thumb: String = ""
)