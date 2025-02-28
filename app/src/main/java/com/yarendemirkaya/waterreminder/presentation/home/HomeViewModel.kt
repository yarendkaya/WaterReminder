package com.yarendemirkaya.waterreminder.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarendemirkaya.waterreminder.common.DataStoreHelper
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.common.toFormattedDate
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
    private val userRepository: UserRepository,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeContract.HomeUiState())
    val uiState: StateFlow<HomeContract.HomeUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeContract.HomeUiEffect>()
    val uiEffect: SharedFlow<HomeContract.HomeUiEffect> = _uiEffect.asSharedFlow()

//    init {
//        Log.d("HomeViewModel", "HomeViewModel Created")
//        checkFirstLogin()
//    }

    private fun addWaterIntake(water: WaterIntake) {
        viewModelScope.launch {
            waterRepository.addWaterIntake(water)
        }
        getWaterIntakes()
    }

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

//                is HomeContract.HomeUiAction.CheckFirstLogin -> {
//                    dataStoreHelper.isFirstLogin.collect { firstLogin ->
//                        _uiState.update {
//                            it.copy(
//                                isFirstLogin = firstLogin,
//                                showBottomSheet = firstLogin
//                            )
//                        }
//                    }
//                }

//                is HomeContract.HomeUiAction.DismissBottomSheet -> {
//                    _uiState.update {
//                        it.copy(showBottomSheet = false)
//                    }
//                }

                is HomeContract.HomeUiAction.OnClickEditProfile -> {
                    _uiEffect.emit(HomeContract.HomeUiEffect.NavigateToEditProfile)
                    viewModelScope.launch { dataStoreHelper.setFirstLoginDone() }
                }
            }
        }
    }

    fun getWaterIntakes() {
        viewModelScope.launch {
            when (val waterIntakes = waterRepository.getWaterIntakes()) {
                is Resource.Success -> {
                    val updatedWaterIntakes = waterIntakes.data.map {
                        it.copy(
                            time = it.time?.toLongOrNull()?.toFormattedDate("dd/MM/yyyy")
                        )
                    }
                    _uiState.value = _uiState.value.copy(waterIntakes = updatedWaterIntakes)
                }

                is Resource.Error -> {
                    _uiEffect.emit(HomeContract.HomeUiEffect.ShowToast(waterIntakes.message))
                }
            }
        }
    }

//    private fun checkFirstLogin() {
//        viewModelScope.launch {
//            dataStoreHelper.isFirstLogin.collect {
//                Log.d("HomeViewModel", "checkFirstLogin: $it")
//                _uiState.update {
//                    it.copy(
//                        isFirstLogin = it.isFirstLogin,
//                        showBottomSheet = it.isFirstLogin
//                    )
//                }
//            }
//        }
//    }

//    fun checkUserProfile(userId: String) {
//        userRepository.checkProfileCompletion(userId) { isCompleted ->
//            _uiState.value = _uiState.value.copy(isProfileCompleted = isCompleted)
//        }
//    }
//
//    fun updateProfileCompleted(userId: String) {
//        userRepository.updateProfileCompletionStatus(userId) { isSuccess ->
//            if (isSuccess) {
//                _uiState.value = _uiState.value.copy(isProfileCompleted = true)
//            }
//        }
//    }
}