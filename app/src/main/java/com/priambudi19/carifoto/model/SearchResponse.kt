package com.priambudi19.carifoto.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results")
    var results: List<Result> = listOf(),
    @SerializedName("total")
    var total: Int = 0,
    @SerializedName("total_pages")
    var totalPages: Int = 0
)