package com.yarendemirkaya.waterreminder.presentation.statistics.weekly

import com.yarendemirkaya.waterreminder.data.models.WaterIntake

object WeeklyStatisticsContract {

    data class WeeklyStatisticsUiState(
        val weeklyIntake: List<WaterIntake> = emptyList(),
        val weeklySuccessPercentage: List<Int> = emptyList(),
        val showInfo: Boolean = false
        )

    sealed class WeeklyStatisticsAction {
        data object OnClickBar : WeeklyStatisticsAction()

    }

    sealed class WeeklyStatisticsEffect {
        data class ShowToast(val message: String) : WeeklyStatisticsEffect()
    }
}