package com.example.weather.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.model.days.Daily
import com.example.weather.model.days.ErrorResponse
import com.example.weather.usecase.ForecastUseCase
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.IOException

class DaysViewModel : ViewModel() {

    val daysList = MutableLiveData<ArrayList<Daily>>()
    val error = MutableLiveData<ErrorResponse>()
    val loading = MutableLiveData<Boolean>()
    private val useCase = ForecastUseCase()

    fun getForecast(
        lat: Double,
        lon: Double,
        units: String?,
        lang: String?,
        exclude: String,
        appid: String
    ) {
        viewModelScope.launch {
            loading.postValue(true)
            val respuesta = useCase.getForecastData(
                lat,
                lon,
                units,
                lang,
                exclude,
                appid
            )
            try {
                if (respuesta.isSuccessful) {
                    daysList.postValue(respuesta.body())
                } else if (respuesta.code() == 404) {
                    val gson = Gson()
                    val errorResponse =
                        gson.fromJson(respuesta.raw().toString(), ErrorResponse::class.java)
                    error.postValue(errorResponse)
                } else {
                    //error.postValue(respuesta.errorBody().toString())
                }
                loading.postValue(false)
            } catch (io: IOException) {
                error.postValue(io.message?.let { ErrorResponse(null, 404, null, null) })
                loading.postValue(false)
            }
        }
    }

}