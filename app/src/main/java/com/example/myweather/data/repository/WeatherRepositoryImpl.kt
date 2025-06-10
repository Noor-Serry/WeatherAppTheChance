package com.example.myweather.data.repository

import android.util.Log
import com.example.myweather.data.mapper.WeatherMapper
import com.example.myweather.data.remote.dto.WeatherResponse
import com.example.myweather.domain.models.LocationCoordinate
import com.example.myweather.domain.models.Weather
import com.example.myweather.domain.repository.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class WeatherRepositoryImpl(
    private val httpClient: HttpClient,
) : WeatherRepository {
    override suspend fun getWeather(locationCoordinate: LocationCoordinate): Weather {
        val countryName = getCountryNameFromIp()
        return WeatherMapper.toDomain(getWeatherRequest(locationCoordinate).get(0))
            .copy(countryName = countryName)
    }

    suspend fun getWeatherRequest(locationCoordinate: LocationCoordinate): List<WeatherResponse> {
        return httpClient.get("https://api.open-meteo.com/v1/forecast") {
            url(API_URL)
            url {
                parameters.append("latitude", locationCoordinate.latitude.toString())
                parameters.append("longitude", locationCoordinate.longitude.toString())
                parameters.append(
                    "current",
                    "temperature_2m,wind_speed_10m,relative_humidity_2m,precipitation_probability,uv_index,pressure_msl,is_day,weather_code,apparent_temperature"
                )
                parameters.append("daily", "temperature_2m_max,temperature_2m_min,weather_code")
                parameters.append("hourly", "temperature_2m,weather_code")
                parameters.append("forecast_days", "8")
            }
        }.body()

    }

    suspend fun getCountryNameFromIp(): String {
        return try {
            val response = httpClient.get("https://ipapi.co/json/").body<Map<String, String?>>()
            response["city"].toString()
        } catch (e: Exception) {
            Log.e("IP geolocation error:"," ${e.message}")
            throw e
        }
    }

    companion object {
        private const val API_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=28.3&longitude=30&current=temperature_2m,wind_speed_10m,relative_humidity_2m,precipitation_probability,uv_index,pressure_msl,is_day,weather_code,apparent_temperature&daily=temperature_2m_max,temperature_2m_min,weather_code&forecast_days=8"
    }
}