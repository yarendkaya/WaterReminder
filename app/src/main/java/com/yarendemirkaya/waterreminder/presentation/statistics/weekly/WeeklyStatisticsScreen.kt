package com.yarendemirkaya.waterreminder.presentation.statistics.weekly

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.yarendemirkaya.waterreminder.presentation.home.components.WaterItem
import com.yarendemirkaya.waterreminder.presentation.statistics.components.CustomBarChart

@Composable
fun WeeklyStatisticsScreen(uiState: WeeklyStatisticsContract.WeeklyStatisticsUiState){

//    LazyColumn {
//        items(uiState.weeklyIntake.size){
//            WaterItem(waterIntake = uiState.weeklyIntake[it],
//                onDeleteClick = {})
//        }
//    }

    CustomBarChart(weeklySuccessPercentage= uiState.weeklySuccessPercentage)
}
