package com.yarendemirkaya.waterreminder.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.data.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterContract.RegisterUiState())
    val uiState: StateFlow<RegisterContract.RegisterUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<RegisterContract.RegisterUiEffect>()
    val uiEffect: SharedFlow<RegisterContract.RegisterUiEffect> = _uiEffect.asSharedFlow()

    fun onAction(action: RegisterContract.RegisterUiAction) {
        viewModelScope.launch {
            when (action) {
                is RegisterContract.RegisterUiAction.SignUpClicked -> signUp()

                is RegisterContract.RegisterUiAction.EmailChanged -> {
                    _uiState.value = _uiState.value.copy(email = action.email)
                }

                is RegisterContract.RegisterUiAction.PasswordChanged -> {
                    _uiState.value = _uiState.value.copy(password = action.password)
                }

                is RegisterContract.RegisterUiAction.SignInClicked -> {
                    _uiEffect.emit(RegisterContract.RegisterUiEffect.GoToLoginScreen)
                }
            }
        }
    }

    private fun signUp() = viewModelScope.launch {
        when (val result = authRepository.register(_uiState.value.email, _uiState.value.password)) {
            is Resource.Success -> {
                emitUiEffect(RegisterContract.RegisterUiEffect.ShowToast(result.data))
                emitUiEffect(RegisterContract.RegisterUiEffect.GoToHomeScreen)
            }

            is Resource.Error -> {
                emitUiEffect(RegisterContract.RegisterUiEffect.ShowToast(result.message))
            }
        }
    }

    private suspend fun emitUiEffect(registerUiEffect: RegisterContract.RegisterUiEffect) {
        _uiEffect.emit(registerUiEffect)
    }
}