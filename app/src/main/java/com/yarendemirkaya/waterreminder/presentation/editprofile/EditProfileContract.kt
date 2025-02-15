package com.yarendemirkaya.waterreminder.presentation.editprofile

import com.yarendemirkaya.waterreminder.data.models.User

object EditProfileContract {

    data class EditProfileUiState(
        val isLoggedIn: Boolean = false,
        val user: User
    )

    sealed class EditProfileUiAction {
        data class OnClickSaveChanges(val user: User) : EditProfileUiAction()
    }

    sealed class EditProfileUiEffect {
        data object NavigateToProfile : EditProfileUiEffect()
    }
}