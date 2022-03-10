package com.example.weather.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    //We use corrutines so the main threat saturated
    suspend fun getWeatherById(
        // The parameters are specified by @query
        @Query("lat") lat: String,
        @Query("lon") lon: String,
//        @Query("id") lon: Long,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        //api key
        @Query("appid") appid: String
//It gets asotiated to this class were is the type
    ): WeatherEntity
}

//Retrofit builds all the call