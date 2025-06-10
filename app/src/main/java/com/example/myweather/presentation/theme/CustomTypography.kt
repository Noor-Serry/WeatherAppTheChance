package com.example.myweather.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class ReplacementTypography(
    val titleMedium: TextStyle,
    val displayLarge: TextStyle,
    val headlineSmall: TextStyle,
    val bodySmall: TextStyle,
    val labelMedium: TextStyle,
    val titleLarge: TextStyle
)

val LocalReplacementTypography = staticCompositionLocalOf {
    getCustomTypography(false)
}

fun getCustomTypography(darkTheme: Boolean): ReplacementTypography {
    val titleMediumColor = if (darkTheme) Color.White else Color(0xFF323232)
    val displayLargeColor = if (darkTheme) Color.White else Color(0xFF060414)
    val headlineSmallColor = if (darkTheme) Color(0x99FFFFFF) else Color(0x99060414)
    val bodySmallColor = if(darkTheme) Color.White  else Color(0xDE060414)
    val labelMediumColor = if(darkTheme) Color(0x99FFFFFF) else Color(0x99060414)
    val titleLargeColor = if(darkTheme) Color.White else Color(0xFF060414)
    val titleMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = .25.sp,
        fontFamily = Urbanist,
        color = titleMediumColor
    )
    val displayLarge =
        TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 64.sp,
            letterSpacing = .25.sp,
            fontFamily = Urbanist,
            color = displayLargeColor
        )
    val headlineSmall =
        TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            letterSpacing = .25.sp,
            fontFamily = Urbanist,
            color = headlineSmallColor
        )
    val bodySmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = .25.sp,
        fontFamily = Urbanist,
        color = bodySmallColor
    )

    val  labelMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = .25.sp,
        fontFamily = Urbanist,
        color = labelMediumColor
    )
    val titleLarge =TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = .25.sp,
        fontFamily = Urbanist,
        color = titleLargeColor
    )
    return ReplacementTypography(
        titleMedium = titleMedium,
        displayLarge = displayLarge,
        headlineSmall = headlineSmall,
        bodySmall = bodySmall,
        labelMedium = labelMedium,
        titleLarge = titleLarge
    )
}

