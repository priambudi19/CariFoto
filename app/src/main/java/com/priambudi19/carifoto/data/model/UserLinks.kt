package com.priambudi19.carifoto.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLinks(
    @SerializedName("html")
    var html: String = ""
): Parcelable