package com.example.myweather.domain.usecase

import com.example.myweather.domain.models.Weather
import com.example.myweather.domain.repository.LocationRepository
import com.example.myweather.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(): Weather {
       val locationCoordinate = locationRepository.getCurrentCoordinate()
       return  weatherRepository.getWeather(locationCoordinate)
    }
}
