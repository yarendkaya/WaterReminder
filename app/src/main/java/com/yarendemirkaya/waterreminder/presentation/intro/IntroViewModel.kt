package com.yarendemirkaya.waterreminder.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarendemirkaya.waterreminder.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(IntroContract.IntroUiState())
    val uiState: MutableStateFlow<IntroContract.IntroUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<IntroContract.IntroUiEffect>()
    val uiEffect: SharedFlow<IntroContract.IntroUiEffect> = _uiEffect.asSharedFlow()


    fun onAction(action: IntroContract.IntroAction) {
        viewModelScope.launch {
            when (action) {
                is IntroContract.IntroAction.LoginClicked -> {
                    _uiEffect.emit(IntroContract.IntroUiEffect.NavigateToLoginScreen)
                }

                is IntroContract.IntroAction.RegisterClicked -> {
                    _uiEffect.emit(IntroContract.IntroUiEffect.NavigateToRegisterScreen)
                }
            }
        }
    }

    private suspend fun emitUiEffect(introUiEffect: IntroContract.IntroUiEffect) {
        _uiEffect.emit(introUiEffect)
    }
}