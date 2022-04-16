package com.priambudi19.carifoto.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.priambudi19.carifoto.data.model.Links
import com.priambudi19.carifoto.data.model.Urls
import com.priambudi19.carifoto.data.model.User

class DataConverter {
    @TypeConverter
    fun fromLinks(value: Links): String {
        val gson = Gson()
        val type = object : TypeToken<Links>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLinks(value: String): Links {
        val gson = Gson()
        val type = object : TypeToken<Links>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromUrls(value: Urls): String {
        val gson = Gson()
        val type = object : TypeToken<Urls>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toUrls(value: String): Urls {
        val gson = Gson()
        val type = object : TypeToken<Urls>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromUser(value: User): String {
        val gson = Gson()
        val type = object : TypeToken<User>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toUser(value: String): User {
        val gson = Gson()
        val type = object : TypeToken<User>() {}.type
        return gson.fromJson(value, type)
    }
}