package com.example.weather.network

import com.example.weather.model.Main
import com.example.weather.model.Sys
import com.example.weather.model.Weather
import com.example.weather.model.Wind

data class WeatherEntity(
    val base: String,
    val main: Main,
    val sys: Sys,
    val id: Int,
    val name: String,
    val wind: Wind,
    val weather: List<Weather>,
    val dt: Long
)
