package com.example.myweather.presentation

import android.app.Application
import com.example.myweather.di.appModule
import com.example.myweather.di.repositoryModule
import com.example.myweather.di.usesCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyWeatherApp)
            modules(repositoryModule, usesCaseModule,appModule)
        }
    }
}