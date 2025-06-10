package com.example.myweather.di

import com.example.myweather.presentation.currentweather.WeatherViewModel
import com.example.myweather.presentation.utils.DefaultDispatcherProvider
import com.example.myweather.presentation.utils.DispatcherProvider
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<DispatcherProvider> {DefaultDispatcherProvider()  }
    viewModelOf(::WeatherViewModel)
}
