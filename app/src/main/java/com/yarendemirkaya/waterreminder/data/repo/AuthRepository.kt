package com.yarendemirkaya.waterreminder.data.repo

import com.yarendemirkaya.waterreminder.data.datasource.AuthDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(private val auth: AuthDataSource) {
    suspend fun login(email: String, password: String) = auth.signIn(email, password)
    suspend fun register(email: String, password: String) = auth.signUp(email, password)

    fun logOut() = auth.logOut()
    fun isUserLoggedIn() = auth.isUserLoggedIn()
}