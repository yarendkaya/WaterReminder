package com.yarendemirkaya.waterreminder.presentation.statistics.monthly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.data.repo.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyStatisticsViewModel @Inject constructor(private val repository: WaterRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(MonthlyStatisticsContract.MonthlyStatisticsUiState())
    val uiState: StateFlow<MonthlyStatisticsContract.MonthlyStatisticsUiState> =
        _uiState.asStateFlow()


    fun onAction(action: MonthlyStatisticsContract.MonthlyStatisticsAction) {
        viewModelScope.launch {
            when (action) {
                is MonthlyStatisticsContract.MonthlyStatisticsAction.OnClickBar -> {
                    _uiState.value = _uiState.value.copy(showInfo = !_uiState.value.showInfo)
                }
            }
        }
    }

    fun getMonthlyIntakeByTime() {
        viewModelScope.launch {
            when (val result = repository.getMonthlyIntakeByTime()) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(monthlyIntake = result.data)
                    monthlySuccessPercentage()
                }

                is Resource.Error -> {}
            }
        }
    }

    private fun monthlySuccessPercentage(){
        viewModelScope.launch {
            val monthlySuccessPercentage = getMonthlySuccessPercentage()
            _uiState.value = _uiState.value.copy(monthlySuccessPercentage = monthlySuccessPercentage)
        }
    }

    private suspend fun getMonthlySuccessPercentage(): List<Int> {
        val monthlyData = repository.getMonthlyIntakeByTime()
        if (monthlyData is Resource.Error) return emptyList()

        val userGoal = 2000 * 30 // Bir ayda 30 gün varsayarak hedef belirleniyor
        val waterIntakes = (monthlyData as Resource.Success).data

        // Aylık bazda su tüketimini gruplamak için bir harita kullanıyoruz
        val monthlyIntakeMap = mutableMapOf<Int, Int>() // <Month, TotalWaterIntake>

        waterIntakes.forEach { intake ->
            intake.time?.toLongOrNull()?.let { timestamp ->
                val calendar = java.util.Calendar.getInstance()
                calendar.timeInMillis = timestamp
                val month = calendar.get(java.util.Calendar.MONTH) // 0 = Ocak, 1 = Şubat, ...

                monthlyIntakeMap[month] = (monthlyIntakeMap[month] ?: 0) + intake.amount
            }
        }

        // Her ayın yüzdesini hesapla (Maksimum %100 olacak şekilde)
        return (0..11).map { month ->
            val totalIntake = monthlyIntakeMap[month] ?: 0
            (totalIntake * 100 / userGoal).coerceIn(0, 100)
        }
    }

}
