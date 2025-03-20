package com.yarendemirkaya.waterreminder.presentation.statistics.weekly

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WeeklyStatisticsScreen(
    uiState: WeeklyStatisticsContract.WeeklyStatisticsUiState,
    maxValue: Int
) {
    val barGraphHeight by remember { mutableStateOf(500.dp) }
    val barGraphWidth by remember { mutableStateOf(25.dp) }

    val scaleYAxisWidth by remember { mutableStateOf(50.dp) }
    val scaleLineWidth by remember { mutableStateOf(2.dp) }


    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barGraphHeight),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {

            val stepCount = 11
            val yAxisValues = List(stepCount) { index -> maxValue - (index * (maxValue / (stepCount - 1))) }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleYAxisWidth),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    yAxisValues.forEach { value ->
                        Text(text = value.toString())
                    }
                }
            }

            Box(modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleLineWidth)
                    .background(Color.Black))


            uiState.weeklySuccessPercentage.forEach {
                Box(
                    modifier = Modifier
                        .padding(start = barGraphWidth, bottom = 2.dp)
                        .clip(CircleShape)
                        .width(barGraphWidth)
                        .fillMaxHeight(it.toFloat() / 100)
                        .background(Color.Black)
                        .clickable {
                            Log.d("WeeklyStatisticsScreen", "Bar clicked")
                        }
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(start=scaleYAxisWidth)
                .fillMaxWidth()
                .height(scaleLineWidth)
                .background(Color.Black)
        )


        Row(
            modifier = Modifier
                .padding(start = scaleYAxisWidth + barGraphWidth + scaleLineWidth)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
        ) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach {
                Text(
                    modifier = Modifier.width(barGraphWidth),
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Preview
@Composable
fun WeeklyStatisticsScreenPreview() {
    WeeklyStatisticsScreen(
        uiState = WeeklyStatisticsContract.WeeklyStatisticsUiState(
            weeklyIntake = emptyList(),
            showInfo = false,
            weeklySuccessPercentage = listOf(20, 40, 60, 80, 100, 50, 100)
        ),
        maxValue = 2000
    )
}

