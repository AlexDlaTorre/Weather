package com.example.weather.model.one

import com.example.weather.model.Main
import com.example.weather.model.Sys
import com.example.weather.model.Weather
import com.example.weather.model.Wind

data class OneEntity(
    val current: Current,
    val daily: List<Daily>,
) : java.io.Serializable
