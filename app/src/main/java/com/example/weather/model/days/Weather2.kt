package com.example.weather.model.days

import com.google.gson.annotations.SerializedName

data class Weather2(
    @SerializedName("main       ") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon       ") var icon: String? = null,
    @SerializedName("weather    ") var weather: List<Weather2>? = null
)
