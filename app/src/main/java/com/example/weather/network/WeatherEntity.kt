package com.example.weather.network

import com.example.weather.model.today.Main
import com.example.weather.model.today.Sys
import com.example.weather.model.today.Weather
import com.example.weather.model.today.Wind

data class WeatherEntity(
    val base: String,
    val main: Main,
    val sys: Sys,
    val id: Int,
    val name: String,
    val wind: Wind,
    val weather: List<Weather>,
    val dt: Long
): java.io.Serializable
