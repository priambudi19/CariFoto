package com.priambudi19.carifoto.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Links(
    @SerializedName("download")
    var download: String = "",
    @SerializedName("download_location")
    var downloadLocation: String = "",
    @SerializedName("html")
    var html: String = "",
    @SerializedName("self")
    var self: String = ""
): Parcelable