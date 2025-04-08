package com.yarendemirkaya.waterreminder.presentation.statistics.weekly


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.presentation.statistics.components.BarInfoCard

@Composable
fun WeeklyStatisticsScreen(
    uiState: WeeklyStatisticsContract.WeeklyStatisticsUiState,
    maxValue: Int,
    onClick: (WeeklyStatisticsContract.WeeklyStatisticsAction) -> Unit
) {
    val barGraphHeight by remember { mutableStateOf(500.dp) }
    val barGraphWidth by remember { mutableStateOf(26.dp) }

    val scaleYAxisWidth by remember { mutableStateOf(50.dp) }
    val scaleLineWidth by remember { mutableStateOf(2.dp) }

//    var isCardVisible by remember { mutableStateOf(false) }
    var selectedBarIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.light_background)),
        verticalArrangement = Arrangement.Top
    ) {
        DatePicker()
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .padding(horizontal = 4.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.light_background),
                contentColor = colorResource(id = R.color.dark_gray)
            ),
            border = BorderStroke(
                2.dp,
                colorResource(id = R.color.app_color)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barGraphHeight),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                val stepCount = 5
                val yAxisValues =
                    List(stepCount) { index -> maxValue - (index * (maxValue / (stepCount - 1))) }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(scaleYAxisWidth),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = 4.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        yAxisValues.forEach { value ->
                            Text(text = value.toString())
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight()
                        .width(scaleLineWidth)
                        .background(colorResource(id = R.color.dark_gray))
                )

                uiState.weeklySuccessPercentage.forEachIndexed { index, percentage ->
                    Box(
                        modifier = Modifier
                            .padding(start = barGraphWidth, bottom = 3.dp)
                            .clip(CircleShape)
                            .width(barGraphWidth)
                            .fillMaxHeight(percentage.toFloat() / 100)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        colorResource(id = R.color.app_color),
                                        colorResource(id = R.color.medium_blue),
                                        colorResource(id = R.color.background),
                                    )
                                )
                            )
                            .clickable {
//                                isCardVisible = selectedBarIndex != index
                                selectedBarIndex = if (selectedBarIndex == index) null else index
                                onClick(WeeklyStatisticsContract.WeeklyStatisticsAction.OnClickBar)
                            }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(start = scaleYAxisWidth)
                    .fillMaxWidth()
                    .height(scaleLineWidth)
                    .background(colorResource(id = R.color.dark_gray))
            )

            Row(
                modifier = Modifier
                    .padding(
                        start = scaleYAxisWidth + barGraphWidth + scaleLineWidth,
                        bottom = 4.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
            ) {
                listOf("Mn", "Tue", "We", "Thu", "Fri", "Sat", "Sun").forEach {
                    Text(
                        modifier = Modifier.width(barGraphWidth),
                        text = it,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.dark_gray)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.showInfo && selectedBarIndex != null) {
            BarInfoCard(successPercentage = uiState.weeklySuccessPercentage[selectedBarIndex!!])
        }
    }
}

@Composable
fun DatePicker() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_arrow_left_24),
            contentDescription = "Arrow Left",
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.app_color))
                .padding(8.dp),
            tint = colorResource(id = R.color.white)
        )
        Text(
            text = "Date",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
        )
        Icon(
            painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = "Arrow Right",
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(id = R.color.app_color))
                .padding(8.dp),
            tint = colorResource(id = R.color.white)
        )
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
        maxValue = 2000,
        onClick = {}
    )
}

