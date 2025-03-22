package com.yarendemirkaya.waterreminder.presentation.statistics.monthly

import com.yarendemirkaya.waterreminder.data.models.WaterIntake

object MonthlyStatisticsContract {

    data class MonthlyStatisticsUiState(
        val monthlyIntake: List<WaterIntake> = emptyList(),
        val monthlyGoal: Int = 0,
        val monthlySuccessPercentage: List<Int> = emptyList(),
        val monthlyTotal: Int = 0,
        val showInfo: Boolean = false
    )

    sealed class MonthlyStatisticsAction {
        data object OnClickBar : MonthlyStatisticsAction()
    }

    sealed class MonthlyStatisticsEffect {
        data class ShowToast(val message: String) : MonthlyStatisticsEffect()
    }
}