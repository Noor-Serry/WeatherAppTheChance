package com.example.myweather.domain.repository

import com.example.myweather.domain.models.LocationCoordinate
import com.example.myweather.domain.models.Weather

interface WeatherRepository {
    suspend fun getWeather(locationCoordinate: LocationCoordinate) : Weather
}