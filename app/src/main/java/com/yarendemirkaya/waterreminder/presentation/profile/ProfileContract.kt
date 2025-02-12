package com.yarendemirkaya.waterreminder.presentation.profile

import com.yarendemirkaya.waterreminder.data.models.User

object ProfileContract {

    data class ProfileUiState(
        val isLoggedIn: Boolean = false,
        val email: String = "",
        val password: String = "",
        val name: String = "",
        val height: String = "",
        val weight: String = "",
        val age: String = "",
        val gender: String = "",
        val dailyWaterGoal: String = "",
        val sleepTime: String = "",
    )

    sealed class ProfileUiAction {
        data class SaveUserData(val user: User) : ProfileUiAction()
        data class UpdateUser(
            val name: String,
            val height: Int,
            val weight: Int,
            val age: Int,
            val gender: String,
            val dailyWaterGoal: Int,
            val sleepTime: String
        ) : ProfileUiAction()
    }

    sealed class ProfileUiEffect {
        data object NavigateToEdit : ProfileUiEffect()
    }
}