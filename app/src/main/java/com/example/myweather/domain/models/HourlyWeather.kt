package com.example.myweather.domain.models

data class HourlyWeather(
    val hour: String,
    val temperature: Int,
    val weatherIcon: WeatherIcon
)
