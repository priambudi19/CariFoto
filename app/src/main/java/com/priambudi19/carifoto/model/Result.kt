package com.priambudi19.carifoto.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("alt_description")
    var altDescription: String = "",

    @SerializedName("created_at")
    var createdAt: String = "",

    @SerializedName("height")
    var height: Int = 0,

    @SerializedName("id")
    var links: Links = Links(),

    @SerializedName("urls")
    var urls: Urls = Urls(),

    @SerializedName("user")
    var user: User = User(),

    @SerializedName("width")
    var width: Int = 0
)