package com.yarendemirkaya.waterreminder.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarendemirkaya.waterreminder.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiEffect = Channel<SplashContract.UiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()


    fun checkIsUserLoggedIn() {
        viewModelScope.launch {
            delay(2000)
            if (authRepository.isUserLoggedIn()) {
                emitUiEffect(SplashContract.UiEffect.NavigateToHome)
            } else {
                emitUiEffect(SplashContract.UiEffect.NavigateToIntro)
            }
        }
    }

    private suspend fun emitUiEffect(splashUiEffect: SplashContract.UiEffect) {
        _uiEffect.send(splashUiEffect)
    }
}