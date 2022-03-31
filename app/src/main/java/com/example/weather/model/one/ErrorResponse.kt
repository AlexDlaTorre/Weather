package com.example.weather.model.one

data class ErrorResponse(
    val protocol: String?,
    val code: Int?,
    val message: String?,
    val url: String?
)
