package com.yarendemirkaya.waterreminder.presentation.profile

import com.yarendemirkaya.waterreminder.data.models.User

object ProfileContract {

    data class ProfileUiState(
        val isLoggedIn: Boolean = false,
        val user:User
    )

    sealed class ProfileUiAction {
        data class OnClickEdit(val user: User) : ProfileUiAction()
    }

    sealed class ProfileUiEffect {
        data object NavigateToEdit : ProfileUiEffect()
    }
}