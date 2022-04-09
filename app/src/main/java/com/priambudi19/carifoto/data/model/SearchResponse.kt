package com.priambudi19.carifoto.data.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results")
    var results: List<Photo> = listOf(),
    @SerializedName("total")
    var total: Int = 0,
    @SerializedName("total_pages")
    var totalPages: Int = 0
)