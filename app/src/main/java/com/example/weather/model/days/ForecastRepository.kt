package com.example.weather.model.days

import com.example.weather.network.WeatherOneCallService2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ForecastRepository {
    val retrofit = RetrofitInstance.getDays().create(WeatherOneCallService2::class.java)

    suspend fun getForecastData(
        lat: Double,
        lon: Double,
        units: String?,
        lang: String?,
        exclude: String,
        appid: String
    ): Response<OneEntity> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.getWeatherByLonLat2(
                lat,
                lon,
                units,
                lang,
                exclude,
                appid
            )
            response
        }
    }
}