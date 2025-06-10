package com.example.myweather.domain.models


enum class WeatherIcon(val code: Int) {
    CLEAR(0),
    MAINLY_CLEAR(1),
    PARTLY_CLOUDY(2),
    OVERCAST(3),
    FOG(45),
    DRIZZLE_LIGHT(51),
    DRIZZLE_MODERATE(53),
    DRIZZLE_DENSE(55),
    FREEZING_DRIZZLE_LIGHT(56),
    FREEZING_DRIZZLE_DENSE(57),
    RAIN_SLIGHT(61),
    RAIN_MODERATE(63),
    RAIN_HEAVY(65),
    FREEZING_RAIN_LIGHT(66),
    FREEZING_RAIN_HEAVY(67),
    SNOW_SLIGHT(71),
    SNOW_MODERATE(73),
    SNOW_HEAVY(75),
    SNOW_GRAINS(77),
    RAIN_SHOWERS_SLIGHT(80),
    RAIN_SHOWERS_MODERATE(81),
    RAIN_SHOWERS_VIOLENT(82),
    SNOW_SHOWERS_SLIGHT(85),
    SNOW_SHOWERS_HEAVY(86),
    THUNDERSTORM_SLIGHT(95),
    THUNDERSTORM_HAIL_SLIGHT(96),
    THUNDERSTORM_HAIL_HEAVY(99);

    companion object {
        fun fromCode(code: Int): WeatherIcon {
            val baseIcon = values().find { it.code == code } ?: CLEAR
            return baseIcon
        }
    }
}