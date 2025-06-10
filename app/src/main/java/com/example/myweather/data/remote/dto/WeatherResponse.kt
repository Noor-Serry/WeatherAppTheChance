package com.example.myweather.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
@Serializable
data class WeatherResponse(
    val current: CurrentWeatherDto,
    val hourly: HourlyWeatherDto,
    val daily: DailyForecastDto
)

@Serializable
data class CurrentWeatherDto(
    @SerialName("temperature_2m")
    val temperature2m: Double,
    @SerialName("wind_speed_10m")
    val windSpeed10m: Double,
    @SerialName("relative_humidity_2m")
    val relativeHumidity2m: Int,
    @SerialName("precipitation_probability")
    val precipitationProbability: Int,
    @SerialName("uv_index")
    val uvIndex: Double,
    @SerialName("pressure_msl")
    val pressureMsl: Double,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("weather_code")
    val weatherCode: Int,
    @SerialName("apparent_temperature")
    val apparentTemperature: Double
)

@Serializable
data class HourlyWeatherDto(
    val time: List<String>,
    @SerialName("temperature_2m")
    val temperature2m: List<Double>,
    @SerialName("weather_code")
    val weatherCode: List<Int>
)

@Serializable
data class DailyForecastDto(
    val time: List<String>,
    @SerialName("temperature_2m_max")
    val temperature2mMax: List<Double>,
    @SerialName("temperature_2m_min")
    val temperature2mMin: List<Double>,
    @SerialName("weather_code")
    val weatherCode: List<Int>
)