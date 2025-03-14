package com.yarendemirkaya.waterreminder.presentation.statistics.monthly

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.yarendemirkaya.waterreminder.presentation.home.components.WaterItem

@Composable
fun MonthlyStatisticsScreen(uiState: MonthlyStatisticsContract.MonthlyStatisticsUiState){

    LazyColumn {
        items(uiState.monthlyIntake.size){
            WaterItem(waterIntake = uiState.monthlyIntake[it],
                onDeleteClick = {})
        }
    }
}