package com.example.weather.network

import com.example.weather.model.one.OneEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherOneCallService {
    @GET("data/2.5/onecall")
    suspend fun getWeatherByLonLat2(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("exclude")exclude:String,
        @Query("appid") appid: String
    ): OneEntity
}