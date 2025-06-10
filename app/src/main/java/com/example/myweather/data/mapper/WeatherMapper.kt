package com.example.myweather.data.mapper


import com.example.myweather.data.remote.dto.DailyForecastDto
import com.example.myweather.data.remote.dto.HourlyWeatherDto
import com.example.myweather.data.remote.dto.WeatherResponse
import com.example.myweather.domain.models.DailyWeather
import com.example.myweather.domain.models.DailyWeather.Days
import com.example.myweather.domain.models.Forecast
import com.example.myweather.domain.models.HourlyWeather
import com.example.myweather.domain.models.Temperature
import com.example.myweather.domain.models.Weather
import com.example.myweather.domain.models.WeatherCondition
import com.example.myweather.domain.models.WeatherIcon
import com.example.myweather.domain.models.WeatherMetrics
import java.time.LocalDate

object WeatherMapper {

    fun toDomain(weatherResponse: WeatherResponse): Weather {
        val countryName =  "Unknown"

        val currentWeather = weatherResponse.current
        val condition = WeatherCondition(
            description = mapWeatherCodeToDescription(currentWeather.weatherCode),
            weatherIcon = WeatherIcon.fromCode(currentWeather.weatherCode)
        )
        val temperature = Temperature(
            current = currentWeather.temperature2m.toInt(),
            min = weatherResponse.daily.temperature2mMin[0].toInt(),
            max = weatherResponse.daily.temperature2mMax[0].toInt(),
            feelsLike = weatherResponse.current.apparentTemperature.toInt()
        )
        val metrics = WeatherMetrics(
            windSpeedKmh = currentWeather.windSpeed10m.toInt(),
            humidityPercent = currentWeather.relativeHumidity2m,
            rainChancePercent = currentWeather.precipitationProbability,
            uvIndex = currentWeather.uvIndex.toInt(),
            pressureHPa = currentWeather.pressureMsl.toInt()
        )

        val dailyData = weatherResponse.daily
        val todayHourly = mapHourlyWeather(weatherResponse.hourly)
        val next7Days = mapDailyWeather(dailyData)
        val forecast = Forecast(
            today = todayHourly,
            next7Days = next7Days
        )

        return Weather(
            countryName = countryName,
            condition = condition,
            temperature = temperature,
            forecast = forecast,
            metrics = metrics,
           isDay =  weatherResponse.current.isDay == 1
        )
    }


    private fun mapWeatherCodeToDescription(weatherCode: Int): String {
        return when (weatherCode) {
            0 -> "Clear sky"
            1, 2, 3 -> "Mainly clear"
            45, 48 -> "Fog"
            51, 53, 55 -> "Drizzle"
            61, 63, 65 -> "Rain"
            71, 73, 75 -> "Snow"
            80, 81, 82 -> "Showers"
            95, 96, 99 -> "Thunderstorm"
            else -> "Unknown"
        }
    }

    private fun mapHourlyWeather(hourlyWeatherDto: HourlyWeatherDto): List<HourlyWeather> {
       val hourlyWeather = mutableListOf<HourlyWeather>()
      hourlyWeatherDto.weatherCode.take(24).forEachIndexed { index,weatherCode ->
          hourlyWeather.add(HourlyWeather(temperature = hourlyWeatherDto.temperature2m[index].toInt(),
              hour = hourlyWeatherDto.time[index].split("T")[1],
              weatherIcon = WeatherIcon.fromCode(weatherCode)))
      }
        return hourlyWeather
    }

    private fun mapDailyWeather(daily: DailyForecastDto): List<DailyWeather> {
        return daily.time.drop(1).mapIndexed { index, dateStr ->
            val localDate = LocalDate.parse(dateStr)
            val dayOfWeek = localDate.dayOfWeek
            val day = Days.values().find { it.name == dayOfWeek.name } ?: throw Exception("can not find the day in Days enum")
            DailyWeather(
                day = day,
                maxTemp = daily.temperature2mMax[index].toInt(),
                minTemp = daily.temperature2mMin[index].toInt(),
                weatherIcon = WeatherIcon.fromCode(daily.weatherCode[index])
            )
        }
    }
}