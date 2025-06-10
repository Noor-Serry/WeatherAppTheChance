package com.example.myweather.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun MyWeatherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val replacementTypography = getCustomTypography(darkTheme)
    val replacementColor = getReplacementWeatherColorSet(darkTheme)
    CompositionLocalProvider(
        LocalReplacementTypography provides replacementTypography,
        LocalReplacementWeatherColorSet provides replacementColor
    ) {
        MaterialTheme(
            content = content
        )
    }
}




object MyWeatherTheme {
    val typography: ReplacementTypography
        @Composable
        get() = LocalReplacementTypography.current
    val colors: ReplacementWeatherColorSet
        @Composable
        get() = LocalReplacementWeatherColorSet.current
}