package com.example.myweather.presentation.currentweather

import androidx.annotation.DrawableRes
import com.example.myweather.R
import com.example.myweather.domain.models.DailyWeather
import com.example.myweather.domain.models.DailyWeather.Days
import com.example.myweather.domain.models.HourlyWeather
import com.example.myweather.domain.models.Weather
import com.example.myweather.domain.models.WeatherIcon

fun weatherToWeatherUiState(weather: Weather): WeatherUiState {
    return WeatherUiState(
        countryName = weather.countryName,
        iconId = getDrawableRes(weather.condition.weatherIcon, weather.isDay),
        temperature = weather.temperature.current,
        isDay = weather.isDay,
        weatherConditionsText = weather.condition.description,
        maxTemperature = weather.temperature.max,
        minTemperature = weather.temperature.min,
        windSpeed = weather.metrics.windSpeedKmh,
        humidity = weather.metrics.humidityPercent,
        rainChance = weather.metrics.rainChancePercent,
        uvIndex = weather.metrics.uvIndex,
        pressure = weather.metrics.pressureHPa,
        feelsLike = weather.temperature.feelsLike,
        todayForecast = weather.forecast.today.map { hourlyWeatherToUi (it,weather.isDay) },
        weekForecast = weather.forecast.next7Days.map { dailyWeatherToUi(it,weather.isDay) }
    )
}

private fun hourlyWeatherToUi(hourlyWeather: HourlyWeather,isDay: Boolean): HourlyForecast {
    return HourlyForecast(
        hour = hourlyWeather.hour,
        temperature = hourlyWeather.temperature,
        iconId = getDrawableRes(hourlyWeather.weatherIcon, isDay)
    )
}

private fun dailyWeatherToUi(dailyWeather: DailyWeather,isDay: Boolean): DailyForecast {
    return DailyForecast(
        dayNameId = dailyWeather.day.toStringRes(),
        maxTemperature = dailyWeather.maxTemp,
        minTemperature = dailyWeather.minTemp,
        iconId = getDrawableRes(dailyWeather.weatherIcon, isDay)
    )
}

@DrawableRes
fun getDrawableRes(weatherIcon: WeatherIcon, isDay: Boolean): Int = when (weatherIcon) {
    WeatherIcon.MAINLY_CLEAR -> if (isDay) R.drawable.mainly_clear else R.drawable.mainly_clear_night
    WeatherIcon.CLEAR -> if (isDay) R.drawable.clear_sky else R.drawable.clear_sky_night
    WeatherIcon.PARTLY_CLOUDY -> if (isDay) R.drawable.partly_cloudy else R.drawable.partly_cloudy_night
    WeatherIcon.OVERCAST -> if (isDay) R.drawable.overcast else R.drawable.overcast_night
    WeatherIcon.FOG -> if (isDay) R.drawable.fog else R.drawable.fog_night
    WeatherIcon.DRIZZLE_LIGHT -> if (isDay) R.drawable.drizzle_light else R.drawable.drizzle_light_night
    WeatherIcon.DRIZZLE_MODERATE -> if (isDay) R.drawable.drizzle_moderate else R.drawable.drizzle_moderate_night
    WeatherIcon.DRIZZLE_DENSE -> if (isDay) R.drawable.drizzle_moderate else R.drawable.drizzle_moderate_night
    WeatherIcon.FREEZING_DRIZZLE_LIGHT -> if (isDay) R.drawable.freezing_drizzle_light else R.drawable.freezing_drizzle_light_night
    WeatherIcon.FREEZING_DRIZZLE_DENSE -> if (isDay) R.drawable.freezing_drizzle_light else R.drawable.freezing_drizzle_light_night
    WeatherIcon.RAIN_SLIGHT -> if (isDay) R.drawable.rain_slight else R.drawable.rain_slight_night
    WeatherIcon.RAIN_MODERATE -> if (isDay) R.drawable.rain_moderate else R.drawable.rain_moderate_night
    WeatherIcon.RAIN_HEAVY -> if (isDay) R.drawable.rain_intensity else R.drawable.rain_intensity_night
    WeatherIcon.FREEZING_RAIN_LIGHT -> if (isDay) R.drawable.freezing_light_night else R.drawable.freezing_light_night
    WeatherIcon.FREEZING_RAIN_HEAVY -> if (isDay) R.drawable.freezing_heavy else R.drawable.freezing_heavy_night
    WeatherIcon.SNOW_SLIGHT -> if (isDay) R.drawable.snow_fall_light else R.drawable.snow_fall_light_night
    WeatherIcon.SNOW_MODERATE -> if (isDay) R.drawable.snow_fall_moderate else R.drawable.snow_fall_moderate_night
    WeatherIcon.SNOW_HEAVY -> if (isDay) R.drawable.snow_fall_intensity else R.drawable.snow_fall_intensity_night
    WeatherIcon.SNOW_GRAINS -> if (isDay) R.drawable.snow_grains else R.drawable.snow_grains_night
    WeatherIcon.RAIN_SHOWERS_SLIGHT -> if (isDay) R.drawable.rain_shower_slight else R.drawable.rain_shower_slight_night
    WeatherIcon.RAIN_SHOWERS_MODERATE -> if (isDay) R.drawable.rain_shower_moderate else R.drawable.rain_shower_moderate_night
    WeatherIcon.RAIN_SHOWERS_VIOLENT -> if (isDay) R.drawable.rain_shower_violent else R.drawable.rain_shower_violent_night
    WeatherIcon.SNOW_SHOWERS_SLIGHT -> if (isDay) R.drawable.snow_shower_slight else R.drawable.snow_shower_slight_night
    WeatherIcon.SNOW_SHOWERS_HEAVY -> if (isDay) R.drawable.snow_shower_heavy else R.drawable.snow_shower_heavy_night
    WeatherIcon.THUNDERSTORM_SLIGHT -> if (isDay) R.drawable.thunderstrom_with_slight_hail else R.drawable.thunderstrom_with_slight_hail_night
    WeatherIcon.THUNDERSTORM_HAIL_SLIGHT -> if (isDay) R.drawable.thunderstrom_slight_or_moderate else R.drawable.thunderstrom_slight_or_moderate_night
    WeatherIcon.THUNDERSTORM_HAIL_HEAVY -> if (isDay) R.drawable.thunderstrom_with_heavy_hail else R.drawable.thunderstrom_with_heavy_hail_night
}

fun Days.toStringRes() : Int = when (this) {
    Days.MONDAY -> R.string.day_monday
    Days.TUESDAY -> R.string.day_tuesday
    Days.WEDNESDAY -> R.string.day_wednesday
    Days.THURSDAY -> R.string.day_thursday
    Days.FRIDAY -> R.string.day_friday
    Days.SATURDAY -> R.string.day_saturday
    Days.SUNDAY -> R.string.day_sunday
}