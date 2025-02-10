package com.yarendemirkaya.waterreminder.presentation.profile

object ProfileContract {

    data class ProfileUiState(
        val isLoggedIn: Boolean = false,
        val email: String = "",
        val password: String = "",
    )

    sealed class ProfileUiAction {
    }

    sealed class ProfileUiEffect {

    }
}