package com.priambudi19.carifoto.model


import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("first_name")
    var firstName: String = "",

    @SerializedName("id")
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
)