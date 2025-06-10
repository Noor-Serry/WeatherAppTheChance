package com.example.myweather.domain.repository

import com.example.myweather.domain.models.LocationCoordinate

interface LocationRepository {
    suspend fun getCurrentCoordinate() : LocationCoordinate
}