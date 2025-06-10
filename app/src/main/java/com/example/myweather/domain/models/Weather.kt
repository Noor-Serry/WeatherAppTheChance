package com.example.myweather.domain.models

data class Weather(
    val countryName: String,
    val condition: WeatherCondition,
    val temperature: Temperature,
    val forecast: Forecast,
    val metrics: WeatherMetrics,
    val isDay: Boolean
)








