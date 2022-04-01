package com.example.weather.usecase

import com.example.weather.model.days.Daily
import com.example.weather.repository.ForecastRepository
import retrofit2.Response

class ForecastUseCase {
    val service = ForecastRepository()

    suspend fun getForecastData(
        lat: Double,
        lon: Double,
        units: String?,
        lang: String?,
        exclude: String,
        appid: String
    ): Response<ArrayList<Daily>> {
        val response = service.getForecastData(
            lat,
            lon,
            units,
            lang,
            exclude,
            appid
        ).body()?.daily
        return Response.success(response)
    }
}