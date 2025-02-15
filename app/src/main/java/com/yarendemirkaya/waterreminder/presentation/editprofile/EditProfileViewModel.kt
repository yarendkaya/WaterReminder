package com.yarendemirkaya.waterreminder.presentation.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class EditProfileViewModel @Inject constructor(private val repository: UserRepository) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow(EditProfileContract.EditProfileUiState(isLoggedIn = false, user = User()))
    val uiState: StateFlow<EditProfileContract.EditProfileUiState> = _uiState.asStateFlow()
    private val _uiEffect = MutableSharedFlow<EditProfileContract.EditProfileUiEffect>()
    val uiEffect: SharedFlow<EditProfileContract.EditProfileUiEffect> = _uiEffect.asSharedFlow()

    fun onAction(action: EditProfileContract.EditProfileUiAction) {
        when (action) {
            is EditProfileContract.EditProfileUiAction.OnClickSaveChanges -> {
                viewModelScope.launch {
                    repository.saveUserData(action.user)
                    _uiEffect.emit(EditProfileContract.EditProfileUiEffect.NavigateToProfile)
                }
            }
        }
    }
}