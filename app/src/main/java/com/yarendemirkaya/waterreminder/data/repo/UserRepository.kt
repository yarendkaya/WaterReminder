package com.yarendemirkaya.waterreminder.data.repo

import com.yarendemirkaya.waterreminder.data.datasource.UserDataSource
import com.yarendemirkaya.waterreminder.data.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDataSource: UserDataSource) {

    suspend fun saveUserData(user: User) {
        userDataSource.saveUserInfo(user)
    }

    fun getUserData(userId: String): Flow<User?> {
        return userDataSource.getUserData(userId)
    }
}