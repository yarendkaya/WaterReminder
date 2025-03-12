package com.yarendemirkaya.waterreminder.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WaterIntakeProgressBar(successPercentage: Float) {
    val gradientColors = listOf(Color(0xFF64B5F6),
        Color(0xFF1976D2),
        Color(0xFF0D47A1),
        Color(0xFF0D47A1))
    val strokeWidth = 12.dp

    Box(
        modifier = Modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.size(160.dp),
            color = Color.Gray.copy(alpha = 0.2f),
            strokeWidth = strokeWidth,
            trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
        )

        Canvas(modifier = Modifier.size(160.dp)) {
            drawArc(
                brush = Brush.linearGradient(gradientColors),
                startAngle = -90f,
                sweepAngle = 360 * successPercentage/100,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${successPercentage.toInt()}%",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}


@Preview
@Composable
fun WaterIntakeProgressBarPreview() {
    WaterIntakeProgressBar(100f)
}

