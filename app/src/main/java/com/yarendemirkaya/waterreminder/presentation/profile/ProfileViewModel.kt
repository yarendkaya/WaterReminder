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

    private val userid = FirebaseAuth.getInstance().currentUser?.uid


    private val _uiState = MutableStateFlow(ProfileContract.ProfileUiState())
    val uiState: StateFlow<ProfileContract.ProfileUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ProfileContract.ProfileUiEffect>()
    val uiEffect: SharedFlow<ProfileContract.ProfileUiEffect> = _uiEffect.asSharedFlow()

    fun onAction(action: ProfileContract.ProfileUiAction) {
        when (action) {
            is ProfileContract.ProfileUiAction.SaveUserData -> saveUserData(action.user)
            is ProfileContract.ProfileUiAction.UpdateUser -> updateUser(
                action.name,
                action.height,
                action.weight,
                action.age,
                action.gender,
                action.dailyWaterGoal,
                action.sleepTime
            )
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
                        name = user.name,
                        height = user.height.toString(),
                        weight = user.weight.toString(),
                        age = user.age.toString(),
                        gender = user.gender,
                        dailyWaterGoal = user.dailyWaterGoal.toString(),
                        sleepTime = user.sleepTime
                    )
                }
                _uiEffect.emit(ProfileContract.ProfileUiEffect.NavigateToEdit)
            }
        }
    }

    private fun saveUserData(user: User) {
        if (userid != null) {
            User(
                name = user.name,
                weight = user.weight,
                height = user.height,
                age = user.age,
                gender = user.gender,
                dailyWaterGoal = user.dailyWaterGoal,
                sleepTime = user.sleepTime,
            )
        }
        viewModelScope.launch {
            userRepo.saveUserData(user)
        }
    }

    fun updateUser(
        name: String,
        height: Int,
        weight: Int,
        age: Int,
        gender: String,
        dailyWaterGoal: Int,
        sleepTime: String
    ) {

        val updatedUser = User(
            name = name,
            height = height,
            weight = weight,
            age = age,
            gender = gender,
            dailyWaterGoal = dailyWaterGoal,
            sleepTime = sleepTime
        )

        viewModelScope.launch {
            userRepo.saveUserData(updatedUser)
        }
    }
}