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


    fun getMonthlyIntakeByTime() {
        viewModelScope.launch {
            when (val result = repository.getMonthlyIntakeByTime()) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(monthlyIntake = result.data)
                }

                is Resource.Error -> {}
            }
        }
    }
}