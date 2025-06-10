package com.example.myweather.di

import android.location.Geocoder
import com.example.myweather.data.repository.LocationRepositoryImpl
import com.example.myweather.data.repository.WeatherRepositoryImpl
import com.example.myweather.domain.repository.LocationRepository
import com.example.myweather.domain.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext

import org.koin.dsl.module

val repositoryModule = module {
    single { Geocoder(androidContext()) }
    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(androidContext())

    }
    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    single<LocationRepository> { LocationRepositoryImpl(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

}

