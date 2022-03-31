package com.example.weather.model.one

import android.icu.text.IDNA
import com.example.weather.model.Main
import com.example.weather.model.Sys
import com.example.weather.model.Weather
import com.example.weather.model.Wind
import com.google.gson.annotations.SerializedName

data class OneEntity(
    @SerializedName("current" ) var current : Current = Current(),
    @SerializedName("daily" ) var daily : ArrayList<Daily> = arrayListOf()
) : java.io.Serializable
