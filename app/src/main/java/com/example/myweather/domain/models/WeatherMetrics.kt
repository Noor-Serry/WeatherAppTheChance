package com.example.myweather.domain.models

data class WeatherMetrics(
    val windSpeedKmh: Int,
    val humidityPercent: Int,
    val rainChancePercent: Int,
    val uvIndex: Int,
    val pressureHPa: Int
)
