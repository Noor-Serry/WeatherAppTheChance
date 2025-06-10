package com.example.myweather.domain.models

data class DailyWeather(
    val day: Days,
    val maxTemp: Int,
    val minTemp: Int,
    val weatherIcon: WeatherIcon
){
    enum class Days {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }
}



