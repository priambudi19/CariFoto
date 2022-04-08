package com.priambudi19.carifoto.model


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("download")
    var download: String = "",
    @SerializedName("download_location")
    var downloadLocation: String = "",
    @SerializedName("html")
    var html: String = "",
    @SerializedName("self")
    var self: String = ""
)