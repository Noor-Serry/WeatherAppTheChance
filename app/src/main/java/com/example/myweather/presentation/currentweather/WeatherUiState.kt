package com.example.myweather.presentation.currentweather

import com.example.myweather.R
import kotlin.uuid.ExperimentalUuidApi

data class WeatherUiState @OptIn(ExperimentalUuidApi::class) constructor(
    val countryName : String = "" ,
    val iconId : Int = R.drawable.rain_shower_moderate,
    val temperature : Int = 0,
    val isDay :Boolean = true,
    val weatherConditionsText : String = "",
    val maxTemperature:Int = 0,
    val minTemperature :Int = 0,
    val windSpeed : Int = 0,
    val humidity: Int = 0,
    val rainChance: Int = 0,
    val uvIndex: Int = 0,
    val pressure: Int = 0,
    val feelsLike: Int = 0,
    val todayForecast: List<HourlyForecast> = emptyList(),
    val weekForecast: List<DailyForecast> = emptyList()
    )
data class HourlyForecast(
    val hour: String,
    val temperature: Int,
    val iconId: Int
)

data class DailyForecast(
    val dayNameId: Int,
    val maxTemperature: Int,
    val minTemperature: Int,
    val iconId: Int
)
