package com.priambudi19.carifoto.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileImage(
    @SerializedName("large")
    var large: String = "",
    @SerializedName("medium")
    var medium: String = "",
    @SerializedName("small")
    var small: String = ""
) : Parcelable