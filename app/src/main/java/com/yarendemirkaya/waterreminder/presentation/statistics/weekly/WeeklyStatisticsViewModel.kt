package com.yarendemirkaya.waterreminder.presentation.statistics.weekly

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
class WeeklyStatisticsViewModel @Inject constructor(private val repository: WaterRepository) :
    ViewModel() {


    private val _uiState = MutableStateFlow(WeeklyStatisticsContract.WeeklyStatisticsUiState())
    val uiState: StateFlow<WeeklyStatisticsContract.WeeklyStatisticsUiState> =
        _uiState.asStateFlow()




    fun getWeeklyIntakeByTime() {
        viewModelScope.launch {
            when (val result = repository.getWeeklyIntakeByTime()) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(weeklyIntake = result.data)
                }

                is Resource.Error -> {

                }
            }
        }
    }

}