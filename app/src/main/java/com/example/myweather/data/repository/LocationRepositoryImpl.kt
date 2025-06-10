package com.example.myweather.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.example.myweather.domain.models.LocationCoordinate
import com.example.myweather.domain.repository.LocationRepository
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Tasks

class LocationRepositoryImpl (val fusedLocationClient: FusedLocationProviderClient)  : LocationRepository{

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCurrentCoordinate(): LocationCoordinate  {
        val location = Tasks.await(
            fusedLocationClient.getCurrentLocation(
                CurrentLocationRequest.Builder()
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                    .build(),
                null
            )
        )

        return LocationCoordinate(location.latitude,location.longitude)
    }

}