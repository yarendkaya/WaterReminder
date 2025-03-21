package com.yarendemirkaya.waterreminder.presentation.statistics.monthly

import com.yarendemirkaya.waterreminder.data.models.WaterIntake

object MonthlyStatisticsContract {

    data class MonthlyStatisticsUiState(
        val monthlyIntake: List<WaterIntake> = emptyList(),
        val monthlyGoal: Int = 0,
        val monthlyAverage: Int = 0,
        val monthlyTotal: Int = 0
    )
}