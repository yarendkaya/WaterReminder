package com.yarendemirkaya.waterreminder.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.common.toFormattedTime
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import com.yarendemirkaya.waterreminder.data.repo.UserRepository
import com.yarendemirkaya.waterreminder.data.repo.WaterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val waterRepository: WaterRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeContract.HomeUiState())
    val uiState: StateFlow<HomeContract.HomeUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeContract.HomeUiEffect>()
    val uiEffect: SharedFlow<HomeContract.HomeUiEffect> = _uiEffect.asSharedFlow()


    fun onAction(action: HomeContract.HomeUiAction) {
        viewModelScope.launch {
            when (action) {
                is HomeContract.HomeUiAction.OnClickAddWaterIntake -> addWaterIntake(action.waterIntake)

                is HomeContract.HomeUiAction.OnClickOpenDialog -> _uiState.update {
                    it.copy(isDialogOpen = true)
                }

                is HomeContract.HomeUiAction.OnClickCloseDialog -> _uiState.update {
                    it.copy(isDialogOpen = false)
                }

                is HomeContract.HomeUiAction.OnClickEditProfile -> {
                    _uiEffect.emit(HomeContract.HomeUiEffect.NavigateToProfile)
                }

                is HomeContract.HomeUiAction.OnClickDeleteWaterIntake -> {
                    deleteWaterIntake(action.waterIntake)
                }
            }
        }
    }

    private fun addWaterIntake(water: WaterIntake) {
        viewModelScope.launch {
            waterRepository.addWaterIntake(water)
            getTodayIntakeByTime()
        }
    }

    internal fun getWaterIntakes() {
        viewModelScope.launch {
            when (val waterIntakes = waterRepository.getWaterIntakes()) {
                is Resource.Success -> {
                    val updatedWaterIntakes = waterIntakes.data.map {
                        it.copy(
                            time = it.time?.toLongOrNull()?.toFormattedTime("HH:mm")
                        )
                    }
                    _uiState.update {
                        it.copy(waterIntakes = updatedWaterIntakes)
                    }
                }

                is Resource.Error -> {
                    _uiEffect.emit(HomeContract.HomeUiEffect.ShowToast(waterIntakes.message))
                }
            }
        }
    }


    internal fun checkUserHasData() {
        viewModelScope.launch {
            val userUid = FirebaseAuth.getInstance().currentUser?.uid
            if (userUid != null) {
                when (val result = userRepository.checkUserHasData(userUid)) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(isAddedInfo = result.data)
                        if (!result.data) {
                            _uiState.update {
                                it.copy(showEditDialog = true)
                            }
                        }
                    }

                    is Resource.Error -> {
                        _uiEffect.emit(HomeContract.HomeUiEffect.ShowToast(result.message))
                    }
                }
            }
        }
    }

    private fun deleteWaterIntake(waterIntake: WaterIntake) {
        viewModelScope.launch {
            when (val result = waterRepository.deleteWaterIntake(waterIntake)) {
                is Resource.Success -> getTodayIntakeByTime()
                is Resource.Error -> _uiEffect.emit(HomeContract.HomeUiEffect.ShowToast(result.message))
            }
        }
    }

    fun getUserName() {
        viewModelScope.launch {
            when (val result = userRepository.getUserName()) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(userName = result.data)
                    }
                }

                is Resource.Error -> {
                    _uiEffect.emit(HomeContract.HomeUiEffect.ShowToast(result.message))
                }
            }
        }
    }


    fun getTodayIntakeByTime() {
        viewModelScope.launch {
            when (val waterIntakes = waterRepository.getTodayIntakeByTime()) {
                is Resource.Success -> {
                    val updatedWaterIntakes = waterIntakes.data.map {
                        it.copy(
                           time = it.time?.toLongOrNull()?.toFormattedTime("HH:mm")
                        )
                    }.sortedBy { it.time }
                    _uiState.update {
                        it.copy(waterIntakes = updatedWaterIntakes,
                            dailyIntake = updatedWaterIntakes.sumOf { it.amount },)
                    }
                    fetchPercentOfSuccess()
                }

                is Resource.Error -> {
                    _uiEffect.emit(HomeContract.HomeUiEffect.ShowToast(waterIntakes.message))
                }
            }
        }
    }

    private fun fetchPercentOfSuccess(){
        val goal=2000
        val percentOfSuccess= _uiState.value.dailyIntake*100/goal
        _uiState.update {
            it.copy(percentOfSuccess = percentOfSuccess)
        }
    }
}