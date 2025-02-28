package com.yarendemirkaya.waterreminder.data.repo

import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.data.datasource.AuthDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(private val auth: AuthDataSource) {
    suspend fun login(email: String, password: String): Resource<String> {
        return auth.signIn(email, password)
    }

    suspend fun register(email: String, password: String): Resource<String> {
        return auth.signUp(email, password)
    }

    fun logOut() = auth.logOut()
    suspend fun isUserLoggedIn() = auth.isUserLoggedIn()
}