package com.yarendemirkaya.waterreminder.presentation.statistics.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val textColor = Color.Black
    val maxYValue = 2000 // Y ekseni maksimum 2000 ml olacak
    val step = 200 // 200'er artacak
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(16.dp)
    ) {
        Canvas(
            modifier = Modifier

        ) {
            for (i in 0..maxYValue step step) {
                val yPos = size.height - (i.toFloat() / maxYValue) * size.height

                drawLine(
                    color = borderColor,
                    start = Offset(0f, yPos),
                    end = Offset(size.width, yPos),
                    strokeWidth = strokeWidth
                )
            }

            drawLine(
                color = borderColor,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = strokeWidth
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            for (i in maxYValue downTo 0 step step) {
                Text(
                    text = "$i ml",
                    fontSize = 12.sp,
                    color = textColor,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

            weeklySuccessPercentage.forEachIndexed { index, value ->
                Box(
                    modifier = Modifier.width(40.dp)
                ) {
                    Bar(value = value, color = barColor, maxHeight = 600.dp)
                    Text(
                        text = daysOfWeek[index],
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}

@Composable
private fun Bar(value: Int, color: Color, maxHeight: Dp) {
    var startAnimation by remember { mutableStateOf(false) }
    var showValue by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val maxHeightPx = with(LocalDensity.current) { maxHeight.toPx() }

    val animatedHeight by animateFloatAsState(//buradaki sorunu çözemezsem ml cinsinden kaldırıcam sadece yüzddeler gözükecek
        targetValue = if (startAnimation) (value) * maxHeightPx else 0f,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
        label = "barHeightAnimation"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .clickable { showValue = !showValue }
    ) {
        if (showValue) {
            Text(
                text = "$value",
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
    CustomBarChart(weeklySuccessPercentage = listOf(10, 15, 20, 5, 10, 80, 25))
}