package com.yarendemirkaya.waterreminder.presentation.register

object RegisterContract {

    data class RegisterUiState(
        val isLoggedIn: Boolean = false,
        val email: String = "",
        val password: String = "",
    )

    sealed class RegisterUiAction {
        data object SignUpClicked : RegisterUiAction()
        data class EmailChanged(val email: String) : RegisterUiAction()
        data class PasswordChanged(val password: String) : RegisterUiAction()
    }

    sealed class RegisterUiEffect {
        data class ShowToast(val message: String) : RegisterUiEffect()
        data object GoToHomeScreen : RegisterUiEffect()
        data object GoToLoginScreen : RegisterUiEffect()
    }
}