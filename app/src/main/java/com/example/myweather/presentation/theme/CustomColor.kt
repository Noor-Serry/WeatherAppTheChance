package com.example.myweather.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ReplacementWeatherColorSet(
    val backgroundGradient: List<Color>,
    val weatherImageBackground: Color,
    val maxMinTemperatureBackground: Color,
    val weatherMetricsCardBorder: Color,
    val weatherMetricsCardBackground: Color,
    val hourlyWeatherCardBorder: Color,
    val hourlyWeatherCardBackground: Color,
    val hourlyWeatherCardTemperatureText: Color,
    val daysCardBorder: Color,
    val textColor: Color,
    val iconTint: Color,
    val weatherLocationHeaderTint: Color,
    val weatherMetricsCardIconTint: Color,
    val nextDaysListBackground : Color
)

val lightMode = ReplacementWeatherColorSet(
    backgroundGradient = listOf(Color(0xFF87CEFA), Color(0xFFFFFFFF)),
    weatherImageBackground = Color(0xFF00619D),
    maxMinTemperatureBackground = Color(0x14060414),
    weatherMetricsCardBorder = Color(0x14060414),
    weatherMetricsCardBackground = Color(0xB3FFFFFF),
    hourlyWeatherCardBorder = Color(0x14060414),
    hourlyWeatherCardBackground = Color(0xB3FFFFFF),
    hourlyWeatherCardTemperatureText = Color(0xDE060414),
    daysCardBorder = Color(0x14060414),
    textColor = Color(0xDE060414),
    iconTint = Color(0xFF060414),
    weatherLocationHeaderTint = Color(0xFF323232),
    weatherMetricsCardIconTint = Color(0xFF87CEFA),
    nextDaysListBackground = Color(0xB3FFFFFF)
)
val darkMode = ReplacementWeatherColorSet(
    backgroundGradient = listOf(Color(0xFF060414), Color(0xFF0D0C19)),
    weatherImageBackground = Color(0xFFC0B7FF),
    maxMinTemperatureBackground = Color(0x14FFFFFF),
    weatherMetricsCardBorder = Color(0x14FFFFFF),
    weatherMetricsCardBackground = Color(0xB2060414),
    hourlyWeatherCardBorder = Color(0x14FFFFFF),
    hourlyWeatherCardBackground = Color(0xB2060414),
    hourlyWeatherCardTemperatureText = Color(0xDEFFFFFF),
    daysCardBorder = Color(0x14FFFFFF),
    textColor = Color(0xDEFFFFFF),
    iconTint = Color(0xFFFFFFFF),
    weatherLocationHeaderTint = Color.White,
    weatherMetricsCardIconTint = Color(0xFF87CEFA),
    nextDaysListBackground = Color(0xB3060414)

)

val LocalReplacementWeatherColorSet = staticCompositionLocalOf {
    getReplacementWeatherColorSet(false)
}


fun getReplacementWeatherColorSet(darkTheme: Boolean) =
     if(darkTheme) darkMode else lightMode


