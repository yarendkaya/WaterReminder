package com.yarendemirkaya.waterreminder.presentation.splash

object SplashContract {
    sealed interface UiEffect {
        data object NavigateToIntro : UiEffect
        data object NavigateToHome : UiEffect
    }
}