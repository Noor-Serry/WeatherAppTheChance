package com.example.myweather.presentation.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun VerticalSpacer(height : Int){
    Spacer(modifier = Modifier.height(height.dp))
}

@Composable
fun HorizontalSpacer(width : Int){
    Spacer(modifier = Modifier.width(width.dp))
}
fun Modifier.bottomBorder(
    strokeWidth: Dp,
    color: Color
): Modifier = this.then(
    Modifier.drawBehind {
        val stroke = strokeWidth.toPx()
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(0f, size.height - stroke / 2),
            end = androidx.compose.ui.geometry.Offset(size.width, size.height - stroke / 2),
            strokeWidth = stroke
        )
    }
)