package com.example.weather.model.days

import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("min") var min: Double? = null,
    @SerializedName("max") var max: Double? = null
) : java.io.Serializable
