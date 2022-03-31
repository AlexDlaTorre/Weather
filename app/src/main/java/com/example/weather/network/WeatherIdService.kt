package com.example.weather.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherIdService {
    @GET("data/2.5/weather")
    suspend fun getWeatherById(
        @Query("id") id:Long,
        @Query("units") units:String?,
        @Query("exclude") exclude: String,
        @Query("appid") appid:String) : WeatherEntity
}