package com.yarendemirkaya.waterreminder.presentation.statistics.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomBarChart(weeklySuccessPercentage: List<Int>) {
    val borderColor = colorResource(id = com.yarendemirkaya.waterreminder.R.color.light_background)
    val barColor = colorResource(id = com.yarendemirkaya.waterreminder.R.color.dark_gray)
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }

    Row(
        modifier = Modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(500.dp)
                .drawBehind {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = strokeWidth
                    )
                }
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        weeklySuccessPercentage.forEach { value ->
            Bar(
                value = value.toFloat(),
                color = barColor,
                maxHeight = 400.dp
            )
        }
    }
}

@Composable
private fun RowScope.Bar(
    value: Float,
    color: Color,
    maxHeight: Dp
) {

    var startAnimation by remember { mutableStateOf(false) }
    var showPercentage by remember { mutableStateOf(false) }
    val animatedHeight by animateFloatAsState(
        targetValue = if (startAnimation) (value / 100) * maxHeight.value else 0f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "barHeightAnimation"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 5.dp)
            .clickable { showPercentage = !showPercentage }
    ) {
        if (showPercentage) {
            Text(
                text = "${value.toInt()}%",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = com.yarendemirkaya.waterreminder.R.color.dark_gray),
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .height(animatedHeight.dp)
                .background(color)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun CustomBarChartPreview() {
    CustomBarChart(weeklySuccessPercentage = listOf(20, 40, 60, 80, 100, 20, 40))
}