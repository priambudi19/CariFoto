package com.priambudi19.carifoto.utils

import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.priambudi19.carifoto.data.model.Links
import com.priambudi19.carifoto.data.model.Urls
import com.priambudi19.carifoto.data.model.User

class DataConverter {
    @TypeConverters
    fun fromLinks(value: Links): String {
        val gson = Gson()
        val type = object : TypeToken<Links>() {}.type
        return gson.toJson(value, type)
    }

     @TypeConverters
    fun toLinks(value: String) : Links{
        val gson = Gson()
        val type = object : TypeToken<Links>() {}.type
        return gson.fromJson(value, type)
    }

     @TypeConverters
    fun fromUrls(value: Urls) : String {
        val gson = Gson()
        val type = object : TypeToken<Urls>() {}.type
        return gson.toJson(value, type)
    }

     @TypeConverters
    fun toUrls(value: String) : Urls{
        val gson = Gson()
        val type = object : TypeToken<Urls>() {}.type
        return gson.fromJson(value, type)
    }

     @TypeConverters
    fun fromUser(value: User): String {
        val gson = Gson()
        val type = object : TypeToken<User>() {}.type
        return gson.toJson(value, type)
    }

     @TypeConverters
    fun toUser(value: String) : User {
        val gson = Gson()
        val type = object : TypeToken<User>() {}.type
        return gson.fromJson(value, type)
    }
}