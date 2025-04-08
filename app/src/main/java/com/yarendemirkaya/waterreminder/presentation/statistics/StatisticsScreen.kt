package com.yarendemirkaya.waterreminder.presentation.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yarendemirkaya.waterreminder.R
import com.yarendemirkaya.waterreminder.presentation.statistics.monthly.MonthlyStatisticsContract
import com.yarendemirkaya.waterreminder.presentation.statistics.monthly.MonthlyStatisticsScreen
import com.yarendemirkaya.waterreminder.presentation.statistics.weekly.WeeklyStatisticsContract
import com.yarendemirkaya.waterreminder.presentation.statistics.weekly.WeeklyStatisticsScreen
import kotlinx.coroutines.launch

@Composable
fun StatisticsViewPager(
    weeklyStatisticsUiState: WeeklyStatisticsContract.WeeklyStatisticsUiState,
    monthlyStatisticsUiState: MonthlyStatisticsContract.MonthlyStatisticsUiState,
    onAction: (WeeklyStatisticsContract.WeeklyStatisticsAction) -> Unit,
    onActionMonthly: (MonthlyStatisticsContract.MonthlyStatisticsAction) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.light_background))
    ) {
        // Header
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp)
        ) {
            listOf("Weekly", "Monthly").forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            style = if (pagerState.currentPage == index)
                                MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            else
                                MaterialTheme.typography.titleMedium
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pager content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                ,
            pageSpacing = 12.dp,
        ) { page ->
            when (page) {
                0 -> WeeklyStatisticsScreen(
                    uiState = weeklyStatisticsUiState,
                    maxValue = 2000,
                    onClick = onAction
                )

                1 -> MonthlyStatisticsScreen(
                    uiState = monthlyStatisticsUiState,
                    maxValue = 2000,
                    onClick = onActionMonthly
                )
            }
        }
    }
}

@Preview
@Composable
fun StatisticsViewPagerPreview() {
    StatisticsViewPager(
        weeklyStatisticsUiState = WeeklyStatisticsContract.WeeklyStatisticsUiState(
            weeklyIntake = emptyList(),
            showInfo = false,
            weeklySuccessPercentage = listOf(20, 40, 60, 80, 100, 50, 100)
        ),
        monthlyStatisticsUiState = MonthlyStatisticsContract.MonthlyStatisticsUiState(
            monthlyIntake = emptyList(),
            showInfo = false,
            monthlySuccessPercentage = listOf(20, 40, 60, 80, 100, 50, 100)
        ),
        onAction = {},
        onActionMonthly = {}
    )
}