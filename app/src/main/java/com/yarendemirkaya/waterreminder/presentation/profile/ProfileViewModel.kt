package com.yarendemirkaya.waterreminder.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yarendemirkaya.waterreminder.data.models.User
import com.yarendemirkaya.waterreminder.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
) : ViewModel() {
    private val userid = FirebaseAuth.getInstance().currentUser?.uid

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState.asStateFlow()

    init {
        getUserData()
    }

    private fun getUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch {
            userRepo.getUserData(userId).collect { user ->
                _userState.value = user
            }
        }
    }

    fun saveUserData(user: User) {
        if (userid != null) {
            User(
                id = userid,
                name = user.name,
                weight = user.weight,
                height = user.height,
                age = user.age,
                gender = user.gender,
                dailyWaterGoal = user.dailyWaterGoal,
                sleepTime = user.sleepTime,
            )
        }
        val updatedUser = userid?.let { user.copy(id = it) }

        viewModelScope.launch {
            userRepo.saveUserData(updatedUser!!)
        }
    }

    fun updateUser(name: String, height: Int, weight: Int, age: Int, gender: String, dailyWaterGoal: Int, sleepTime: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val updatedUser = User(
            id = userId,
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