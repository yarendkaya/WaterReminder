package com.yarendemirkaya.waterreminder.presentation.intro

object IntroContract {

    data class IntroUiState(
        val isLoggedIn: Boolean = false,
        val isRegistered: Boolean = false
    )

    sealed class IntroAction {
        data object LoginClicked : IntroAction()
        data object RegisterClicked : IntroAction()
    }

    sealed class IntroUiEffect {
        data object NavigateToLoginScreen : IntroUiEffect()
        data object NavigateToRegisterScreen : IntroUiEffect()
        data object NavigateToHomeScreen : IntroUiEffect()
    }
}