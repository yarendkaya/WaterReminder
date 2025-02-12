package com.yarendemirkaya.waterreminder.presentation.home

import com.yarendemirkaya.waterreminder.data.models.WaterIntake

object HomeContract {

    data class HomeUiState(
        val isLoggedIn: Boolean = false,
        val waterIntakes: List<WaterIntake> = emptyList(),
        val waterIntake: WaterIntake? = null,
    )

    sealed class HomeUiAction {
        data class AddWaterIntake(val waterIntake: WaterIntake) : HomeUiAction()

    }

    sealed class HomeUiEffect {
        data class ShowToast(val message: String) : HomeUiEffect()
    }
}