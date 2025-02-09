package com.yarendemirkaya.waterreminder.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginContract.LoginUiState())
    val uiState: StateFlow<LoginContract.LoginUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<LoginContract.LoginUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        isUserLoggedIn()
    }

    fun onAction(action: LoginContract.LoginUiAction) {
        when (action) {
            is LoginContract.LoginUiAction.SignInClicked -> signIn()

            is LoginContract.LoginUiAction.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = action.email)
            }

            is LoginContract.LoginUiAction.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = action.password)
            }
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            when (val result = authRepository.signIn(uiState.value.email, uiState.value.password)) {
                is Resource.Success -> {
                    emitUiEffect(LoginContract.LoginUiEffect.ShowToast(result.data))
                    emitUiEffect(LoginContract.LoginUiEffect.GoToHomeScreen)
                }
                is Resource.Error -> {
                    emitUiEffect(LoginContract.LoginUiEffect.ShowToast(result.message))
                }
            }
        }
    }

    private fun isUserLoggedIn() = viewModelScope.launch {
        if (authRepository.isUserLoggedIn()) {
            emitUiEffect(LoginContract.LoginUiEffect.GoToHomeScreen)
        } else {
            //navigate to login screen
        }
    }

    private suspend fun emitUiEffect(loginUiEffect: LoginContract.LoginUiEffect) {
        _uiEffect.send(loginUiEffect)
    }
}