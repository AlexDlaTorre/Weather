package com.example.weather.model.days

import com.google.gson.annotations.SerializedName

data class OneEntity(
    @SerializedName("current") var current: Current = Current(),
    @SerializedName("daily") var daily: ArrayList<Daily> = arrayListOf()
) : java.io.Serializable
