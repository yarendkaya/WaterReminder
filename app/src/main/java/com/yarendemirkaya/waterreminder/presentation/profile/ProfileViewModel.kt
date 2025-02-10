package com.yarendemirkaya.waterreminder.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yarendemirkaya.waterreminder.data.models.User
import com.yarendemirkaya.waterreminder.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepository,
) : ViewModel() {
    private val userid = FirebaseAuth.getInstance().currentUser?.uid

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
        viewModelScope.launch {
            userRepo.saveUserData(user)
        }
    }
}