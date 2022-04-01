package com.example.weather.model.days

data class ErrorResponse(
    val protocol: String?,
    val code: Int?,
    val message: String?,
    val url: String?
)
