package com.example.weather.model.one

import com.example.weather.model.Weather

data class Daily(
    val dt: Long,
    val temp: Temp,

): java.io.Serializable
