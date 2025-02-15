package com.yarendemirkaya.waterreminder.presentation.home

import com.yarendemirkaya.waterreminder.data.models.WaterIntake

object HomeContract {

    data class HomeUiState(
        val isLoggedIn: Boolean = false,
        val waterIntakes: List<WaterIntake> = emptyList(),
        val waterIntake: WaterIntake? = null,
        val isDialogOpen: Boolean = false
    )

    sealed class HomeUiAction {
        data class OnClickAddWaterIntake(val waterIntake: WaterIntake) : HomeUiAction()
        data object OnClickOpenDialog : HomeUiAction()
        data object OnClickCloseDialog : HomeUiAction()
    }

    sealed class HomeUiEffect {
        data class ShowToast(val message: String) : HomeUiEffect()
    }
}