package com.yarendemirkaya.waterreminder.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import com.yarendemirkaya.waterreminder.data.repo.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val waterRepository: WaterRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeContract.HomeUiState())
    val uiState: StateFlow<HomeContract.HomeUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeContract.HomeUiEffect>()
    val uiEffect: SharedFlow<HomeContract.HomeUiEffect> = _uiEffect.asSharedFlow()


    private fun addWaterIntake(water: WaterIntake) {
        viewModelScope.launch {
            waterRepository.addWaterIntake(water)
        }
    }

    fun onAction(action: HomeContract.HomeUiAction) {
        viewModelScope.launch {
            when (action) {
                is HomeContract.HomeUiAction.AddWaterIntake -> addWaterIntake(action.waterIntake)
            }
        }
    }

    fun getWaterIntakes() {
        viewModelScope.launch {
            when (val waterIntakes = waterRepository.getWaterIntakes()) {
                is Resource.Success<*> -> {
                    _uiState.value = _uiState.value.copy(waterIntakes = waterIntakes.data as List<WaterIntake>)
                }
                else ->{

                }
            }
        }
    }
}