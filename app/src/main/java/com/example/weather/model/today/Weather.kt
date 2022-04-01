package com.example.weather.model.today

data class Weather(
    val main: String,
    val description: String,
    val icon: String,
    val weather: List<Weather>
)
