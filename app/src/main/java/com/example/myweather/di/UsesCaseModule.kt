package com.example.myweather.di

import com.example.myweather.domain.usecase.GetWeatherUseCase
import org.koin.dsl.module


val usesCaseModule = module {
    single<GetWeatherUseCase> { GetWeatherUseCase(get(),get()) }
}
