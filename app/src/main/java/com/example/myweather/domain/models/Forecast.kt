package com.example.myweather.domain.models

data class Forecast(
    val today: List<HourlyWeather>,
    val next7Days: List<DailyWeather>
)
