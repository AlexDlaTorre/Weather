package com.example.weather.model.one

import com.example.weather.model.Weather
import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("dt") var dt: Long? = null,
    @SerializedName("sunrise") var sunrise: Long? = null,
    @SerializedName("sunset") var sunset: Long? = null,
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("feels_like") var feels_like: Double? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("wind_speed") var wind_speed: Double? = null,
    @SerializedName("weather") var weather: List<Weather>? = null
) : java.io.Serializable
