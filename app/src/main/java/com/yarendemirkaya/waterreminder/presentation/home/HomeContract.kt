package com.yarendemirkaya.waterreminder.presentation.home

import com.yarendemirkaya.waterreminder.data.models.WaterIntake

object HomeContract {

    data class HomeUiState(
        val isLoggedIn: Boolean = false,
        val waterIntakes: List<WaterIntake> = emptyList(),
        val waterIntake: WaterIntake= WaterIntake(),
        val isDialogOpen: Boolean = false,
        val isFirstLogin: Boolean = true,
        val showBottomSheet: Boolean = false
    )

    sealed class HomeUiAction {
        data class OnClickAddWaterIntake(val waterIntake: WaterIntake) : HomeUiAction()
        data object OnClickOpenDialog : HomeUiAction()
        data object OnClickCloseDialog : HomeUiAction()
        data object CheckFirstLogin : HomeUiAction()
        data object DismissBottomSheet : HomeUiAction()
        data object EditProfileClicked : HomeUiAction()
    }

    sealed class HomeUiEffect {
        data class ShowToast(val message: String) : HomeUiEffect()
        data object NavigateToEditProfile : HomeUiEffect()
    }
}