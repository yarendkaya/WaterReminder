package com.yarendemirkaya.waterreminder.presentation.login

object LoginContract {

    data class LoginUiState(
        val isLoggedIn: Boolean = false,
        val email: String = "testo48@test.com",
        val password: String = "test123456",
    )

    sealed class LoginUiAction {
        data object SignInClicked : LoginUiAction()
        data class EmailChanged(val email: String) : LoginUiAction()
        data class PasswordChanged(val password: String) : LoginUiAction()
    }

    sealed class LoginUiEffect {
        data class ShowToast(val message: String) : LoginUiEffect()
        data object GoToHomeScreen : LoginUiEffect()
        data object GoToRegisterScreen : LoginUiEffect()
    }
}