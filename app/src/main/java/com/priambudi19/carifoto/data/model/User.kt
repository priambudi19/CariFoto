package com.priambudi19.carifoto.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    @SerializedName("first_name")
    var firstName: String = "",

    @SerializedName("id", alternate = ["id_user"])
    var id: String = "",

    @SerializedName("last_name")
    var lastName: String = "",

    @SerializedName("links")
    var userLinks: UserLinks = UserLinks(),

    @SerializedName("location")
    var location: String = "",

    @SerializedName("name")
    var name: String = "",

    @SerializedName("profile_image")
    var profileImage: ProfileImage = ProfileImage(),

    @SerializedName("username")
    var username: String = ""
) : Parcelable