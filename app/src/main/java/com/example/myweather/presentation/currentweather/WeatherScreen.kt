package com.example.myweather.presentation.currentweather

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import com.example.myweather.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.myweather.presentation.composables.HorizontalSpacer
import com.example.myweather.presentation.composables.TextWithIcon
import com.example.myweather.presentation.composables.VerticalSpacer
import com.example.myweather.presentation.composables.bottomBorder
import com.example.myweather.presentation.theme.MyWeatherTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel(),
                  onSwitchThemeListener : (isDarkMode : Boolean) ->Unit
                  ) {
    val state = viewModel.state.collectAsState()
    val defaultWeatherUiState = WeatherUiState()
    if (state.value != defaultWeatherUiState) {
        LaunchedEffect(state.value.isDay) {
            onSwitchThemeListener(!state.value.isDay)
        }
        WeatherContent(state = state.value)
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMotionApi::class)
@Composable
private fun WeatherContent(
    state: WeatherUiState
) {
    val scrollState = rememberScrollState()
    val progress by animateFloatAsState(
        targetValue = if (scrollState.value > 10) 1f else 0f,
        animationSpec = tween(500)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = MyWeatherTheme.colors.backgroundGradient))
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(Modifier.statusBarsPadding()){}
        VerticalSpacer(24)
        WeatherLocationHeader(location = state.countryName)
        val context = LocalContext.current
        val motionSceneContent = remember {
            context.resources
                .openRawResource(R.raw.image_weather_details_scene)
                .readBytes()
                .decodeToString()
        }
        MotionLayout(motionScene = MotionScene(motionSceneContent),
            progress = progress,
            modifier = Modifier
                .fillMaxWidth(),
            ){
            WeatherImage(
                modifier = Modifier.layoutId("image")
                    .offset(y = -12.dp), painter = painterResource(state.iconId)
            )
            WeatherDetails(modifier =
                Modifier.layoutId("weatherDetails"),
                temperature = state.temperature,
                weatherConditionsText = state.weatherConditionsText,
                maxTemperature = state.maxTemperature,
                minTemperature = state.minTemperature
            )
        }

        VerticalSpacer(24)
        WeatherMetricsGrid(
            modifier = Modifier.height(236.dp),
            windSpeedKmh = state.windSpeed,
            humidityPercent = state.humidity,
            rainChancePercent = state.rainChance,
            uvIndex = state.uvIndex,
            pressureHPa = state.pressure,
            feelsLike = state.feelsLike
        )
        VerticalSpacer(24)
        Text(
            text = "Today",
            style = MyWeatherTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 12.dp)
        )
        VerticalSpacer(24)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(state.todayForecast) { item ->
                var visible by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { visible = true }

                AnimatedVisibility(visible) {
                    HourlyWeatherCard(
                        modifier = Modifier.animateItem(),
                        temperatureDegree = item.temperature, hour = item.hour,
                        painter = painterResource(item.iconId),
                    )}
            }
        }
        VerticalSpacer(24)
        Text(
            text = "Next 7 days",
            style = MyWeatherTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 12.dp)
        )
        VerticalSpacer(12)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .border(1.dp, MyWeatherTheme.colors.daysCardBorder, RoundedCornerShape(24.dp))
                .clipToBounds().background(MyWeatherTheme.colors.nextDaysListBackground, RoundedCornerShape(24.dp))
        ) {
            state.weekForecast.forEachIndexed { index, item ->
                val isLast = index == state.weekForecast.size - 1
                DaysCard(
                    dayName = stringResource(item.dayNameId),
                    minTemperature = item.minTemperature,
                    maxTemperature = item.maxTemperature,
                    weatherPainter = painterResource(item.iconId),
                    borderColor = if (isLast) Color.Transparent else MyWeatherTheme.colors.daysCardBorder
                )

            }
        }
        VerticalSpacer(32)
        Box(modifier = Modifier.navigationBarsPadding())
    }


}

@Composable
private fun WeatherDetails(
    modifier: Modifier = Modifier,
    temperature: Int,
    weatherConditionsText: String,
    maxTemperature: Int,
    minTemperature: Int
) {

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$temperature°C",
            style = MyWeatherTheme.typography.displayLarge
        )
        Text(
            text = weatherConditionsText,
            style = MyWeatherTheme.typography.headlineSmall
        )
        VerticalSpacer(12)
        MaxAndMinTemperature(
            maxTemperature = maxTemperature,
            minTemperature = minTemperature,
        )
    }
}

@Composable
private fun WeatherLocationHeader(modifier: Modifier = Modifier, location: String) {
    TextWithIcon(
        modifier = modifier, text = location,
        style = MyWeatherTheme.typography.titleMedium
    ) {
        Icon(
            painter = painterResource(id = R.drawable.location_05),
            contentDescription = "Location Pin",
            tint = MyWeatherTheme.colors.weatherLocationHeaderTint,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
private fun WeatherImage(modifier: Modifier = Modifier, painter: Painter) {
    val backgroundColor = MyWeatherTheme.colors.weatherImageBackground
    Box(modifier = modifier,contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .size(220.dp, 200.dp)
                .align(Alignment.Center)
                .blur(250.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
        ) {
            drawCircle(
                color = backgroundColor,
                radius = size.minDimension / 2,
                center = Offset(size.width / 2, size.height / 2)
            )
        }
        Image(
            painter = painter, modifier = Modifier
                .height(200.dp).widthIn(220.dp,227.dp)
                    ,contentDescription = ""
        )
    }
}
@Composable
private fun MaxAndMinTemperature(
    modifier: Modifier = Modifier,
    maxTemperature: Int,
    minTemperature: Int
) {
    TemperatureRange(
        modifier = modifier
            .background(MyWeatherTheme.colors.maxMinTemperatureBackground, RoundedCornerShape(100.dp))
            .padding(horizontal = 24.dp, vertical = 8.dp),
        maxTemperature = maxTemperature,
        minTemperature = minTemperature,
        horizontalArrangement = Arrangement.Center,
        spacerWidth = 8
    )
}

@Composable
private fun DaysCard(
    modifier: Modifier = Modifier,
    dayName: String,
    weatherPainter: Painter,
    maxTemperature: Int,
    minTemperature: Int,
    borderColor: Color
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .bottomBorder(1.dp, borderColor)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = dayName,
            style = MyWeatherTheme.typography.labelMedium,
            fontSize = 16.sp
        )
        Image(
            painter = weatherPainter,
            contentDescription = "",
            modifier = Modifier.size(91.dp, 45.dp)
        )
        TemperatureRange(
            modifier = Modifier.weight(1f),
            maxTemperature = maxTemperature,
            minTemperature = minTemperature,
            horizontalArrangement = Arrangement.End,
            spacerWidth = 4,
            fontSize = 14.sp
        )
    }
}
@Composable
private fun TemperatureRange(
    modifier: Modifier = Modifier,
    maxTemperature: Int,
    minTemperature: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    spacerWidth: Int = 8,
    fontSize: TextUnit = MyWeatherTheme.typography.headlineSmall.fontSize
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextWithIcon(
            text = "$maxTemperature°C",
            style = MyWeatherTheme.typography.headlineSmall,
            color = MyWeatherTheme.colors.textColor,
            fontSize = fontSize
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_down_04),
                contentDescription = "",
                modifier = Modifier.size(12.dp),
                tint = MyWeatherTheme.colors.iconTint
            )
        }
        HorizontalSpacer(spacerWidth)
        Icon(
            painter = painterResource(R.drawable.line_1),
            contentDescription = "",
            modifier = Modifier.size(1.dp, 14.dp),
            tint = MyWeatherTheme.colors.iconTint
        )
        HorizontalSpacer(spacerWidth)
        TextWithIcon(
            text = "${minTemperature}°C",
            style = MyWeatherTheme.typography.headlineSmall,
            color = MyWeatherTheme.colors.textColor,
            fontSize = fontSize
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_down_04_1),
                contentDescription = "",
                modifier = Modifier.size(12.dp),
                tint = MyWeatherTheme.colors.iconTint
            )
        }
    }
}
@Composable
private fun WeatherMetricsGrid(
    modifier: Modifier = Modifier,
    windSpeedKmh: Int,
    humidityPercent: Int,
    rainChancePercent: Int,
    uvIndex: Int,
    pressureHPa: Int,
    feelsLike: Int
) {
    Column(
        modifier = modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            WeatherMetricsCard(
                modifier.weight(1f),
                metricsName = "Wind", metricsValue = "$windSpeedKmh KM/h",
                painter = painterResource(R.drawable.fast_wind)
            )
            WeatherMetricsCard(
                modifier.weight(1f),
                metricsName = "Humidity", metricsValue = "$humidityPercent%",
                painter = painterResource(R.drawable.humidity)
            )
            WeatherMetricsCard(
                modifier.weight(1f),
                metricsName = "Rain", metricsValue = "$rainChancePercent%",
                painter = painterResource(R.drawable.rain)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            WeatherMetricsCard(
                modifier.weight(1f),
                metricsName = "UV Index", metricsValue = "$uvIndex",
                painter = painterResource(R.drawable.uv_02)
            )
            WeatherMetricsCard(
                modifier.weight(1f),
                metricsName = "Pressure", metricsValue = "$pressureHPa hPa",
                painter = painterResource(R.drawable.arrow_down_05)
            )
            WeatherMetricsCard(
                modifier.weight(1f),
                metricsName = "Feels like", metricsValue = "$feelsLike°C",
                painter = painterResource(R.drawable.temperature)
            )
        }
    }
}

@Composable
private fun WeatherMetricsCard(
    modifier: Modifier = Modifier,
    metricsName: String,
    metricsValue: String,
    painter: Painter
) {
    Column(
        modifier
            .background(MyWeatherTheme.colors.weatherMetricsCardBackground, RoundedCornerShape(24.dp))
            .border(1.dp, MyWeatherTheme.colors.weatherMetricsCardBorder, RoundedCornerShape(24.dp))
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter,
            contentDescription = "",
            tint = MyWeatherTheme.colors.weatherMetricsCardIconTint,
            modifier = Modifier.size(32.dp)
        )
        VerticalSpacer(8)
        Text(text = metricsValue, style = MyWeatherTheme.typography.bodySmall)
        VerticalSpacer(2)
        Text(text = metricsName, style = MyWeatherTheme.typography.labelMedium)
    }
}

@Composable
private fun HourlyWeatherCard(
    modifier: Modifier = Modifier,
    temperatureDegree: Int,
    hour: String,
    painter: Painter
) {
    val borderColor = MyWeatherTheme.colors.hourlyWeatherCardBorder
    Column(
        modifier
            .background(MyWeatherTheme.colors.hourlyWeatherCardBackground, RoundedCornerShape(20.dp))
            .drawBehind {
                drawRoundRect(
                    color = borderColor,
                    cornerRadius = CornerRadius(20.dp.toPx()),
                    style = Stroke(width = 1.dp.toPx())
                )
            }
            .padding(horizontal = 12.dp)
            .offset(y = -12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter, contentDescription = "", modifier = Modifier.size(60.dp))
        VerticalSpacer(16)
        Text(
            text = "$temperatureDegree°C",
            style = MyWeatherTheme.typography.headlineSmall,
            color = MyWeatherTheme.colors.hourlyWeatherCardTemperatureText
        )
        VerticalSpacer(4)
        Text(text = "$hour", style = MyWeatherTheme.typography.headlineSmall)
    }
}


