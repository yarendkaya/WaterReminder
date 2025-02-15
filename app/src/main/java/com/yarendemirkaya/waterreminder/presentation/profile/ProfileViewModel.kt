package com.yarendemirkaya.waterreminder.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yarendemirkaya.waterreminder.data.models.User
import com.yarendemirkaya.waterreminder.data.repo.UserRepository
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
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(ProfileContract.ProfileUiState(isLoggedIn = false, user = User()))
    val uiState: StateFlow<ProfileContract.ProfileUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ProfileContract.ProfileUiEffect>()
    val uiEffect: SharedFlow<ProfileContract.ProfileUiEffect> = _uiEffect.asSharedFlow()

    fun onAction(action: ProfileContract.ProfileUiAction) {
        when (action) {
            is ProfileContract.ProfileUiAction.OnClickEdit -> {
                viewModelScope.launch {
                    _uiEffect.emit(ProfileContract.ProfileUiEffect.NavigateToEdit)
                }
            }
        }
    }
    init {
        getUserData()
    }

    private fun getUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            userRepo.getUserData(userId).collect { user ->
                if (user != null) {
                    _uiState.value = _uiState.value.copy(
                        user = user,
                        isLoggedIn = true
                    )
                }
                _uiEffect.emit(ProfileContract.ProfileUiEffect.NavigateToEdit)
            }
        }
    }
}