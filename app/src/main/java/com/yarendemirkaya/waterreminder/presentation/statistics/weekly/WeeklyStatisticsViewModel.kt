package com.yarendemirkaya.waterreminder.presentation.statistics.weekly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.data.repo.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class WeeklyStatisticsViewModel @Inject constructor(private val repository: WaterRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(WeeklyStatisticsContract.WeeklyStatisticsUiState())
    val uiState: StateFlow<WeeklyStatisticsContract.WeeklyStatisticsUiState> =
        _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<WeeklyStatisticsContract.WeeklyStatisticsEffect>()
    val uiEffect: SharedFlow<WeeklyStatisticsContract.WeeklyStatisticsEffect> = _uiEffect.asSharedFlow()


    fun onAction(action: WeeklyStatisticsContract.WeeklyStatisticsAction) {
        viewModelScope.launch {
            when (action) {
                is WeeklyStatisticsContract.WeeklyStatisticsAction.OnClickBar -> {
                    _uiState.value = _uiState.value.copy(showInfo = true)
                }
            }
        }
    }


    fun getWeeklyIntakeByTime() {
        viewModelScope.launch {
            when (val result = repository.getWeeklyIntakeByTime()) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(weeklyIntake = result.data)
                    weeklySuccessPercentage()
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun weeklySuccessPercentage() {
        viewModelScope.launch {
            val weeklySuccessPercentage = getWeeklySuccessPercentage()
            _uiState.value = _uiState.value.copy(weeklySuccessPercentage = weeklySuccessPercentage)
        }
    }


    private suspend fun getWeeklySuccessPercentage(): List<Int> {
        val weeklyData = repository.getWeeklyIntakeByTime()
        if (weeklyData is Resource.Error) return emptyList()

        val userGoal = 2000

        val dailyIntake = MutableList(7) { 0 }

        val waterIntakes = (weeklyData as Resource.Success).data
        val calendar = Calendar.getInstance()

        waterIntakes.forEach { intake ->
            intake.time?.toLongOrNull()?.let { timestamp ->
                calendar.timeInMillis = timestamp
                val dayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1
                dailyIntake[dayIndex] += intake.amount
            }
        }
        return dailyIntake.map { (it * 100 / userGoal).coerceIn(0, 100) }
    }
}
