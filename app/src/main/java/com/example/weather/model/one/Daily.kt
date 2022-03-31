package com.example.weather.model.one

import com.example.weather.model.Weather
import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("dt") var dt: Long? = null,
    @SerializedName("temp") var temp: Temp? = null

) : java.io.Serializable
