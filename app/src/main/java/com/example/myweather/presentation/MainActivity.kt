package com.example.myweather.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.myweather.presentation.currentweather.WeatherScreen
import com.example.myweather.presentation.theme.MyWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isDataLoaded = true
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition{
            Log.d("installSplashScreen", ""+isDataLoaded)
            isDataLoaded
        }
        setContent {
            var isDarkMode by rememberSaveable { mutableStateOf(true) }
            MyWeatherTheme (isDarkMode) {
            WeatherScreen{
                isDarkMode = it
                isDataLoaded = false
                }
            }
        }
    }
}
