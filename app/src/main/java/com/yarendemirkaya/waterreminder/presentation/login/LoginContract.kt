package com.yarendemirkaya.waterreminder.presentation.login

object LoginContract {

    data class LoginUiState(
        val isLoggedIn: Boolean = false,
        val email: String = "",
        val password: String = "",
    )

    sealed class LoginUiAction {
        data object SignInClicked : LoginUiAction()
        data object SignUpClicked : LoginUiAction()
        data class EmailChanged(val email: String) : LoginUiAction()
        data class PasswordChanged(val password: String) : LoginUiAction()
    }

    sealed class LoginUiEffect {
        data class ShowToast(val message: String) : LoginUiEffect()
        data object GoToHomeScreen : LoginUiEffect()
        data object GoToRegisterScreen : LoginUiEffect()
        data object GoToLoginScreen : LoginUiEffect()
    }
}