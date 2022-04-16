package com.priambudi19.carifoto.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_photos")
data class Photo(
    @PrimaryKey
    @SerializedName("id")
    var id: String = "",

    @SerializedName("alt_description")
    var altDescription: String? = "",

    @SerializedName("description")
    var description: String? = "",

    @SerializedName("created_at")
    var createdAt: String = "",

    @SerializedName("height")
    var height: Int = 0,

    @SerializedName("links")
    var links: Links = Links(),

    @SerializedName("urls")
    var urls: Urls = Urls(),

    @SerializedName("user")
    var user: User = User(),

    @SerializedName("width")
    var width: Int = 0
): Parcelable