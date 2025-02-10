package com.yarendemirkaya.waterreminder.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yarendemirkaya.waterreminder.data.models.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserDataSource @Inject constructor(private val fireStore: FirebaseFirestore, private val auth: FirebaseAuth) {

    private val userCollectionRef = fireStore.collection("users")
    private val userid = FirebaseAuth.getInstance().currentUser?.uid


    suspend fun saveUserInfo(user: User) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            try {
                fireStore.collection("users").document(currentUser.uid).set(user).await()

            } catch (e: Exception) {
                println(e.localizedMessage ?: "Failed to save user info")
            }
        }
    }


    fun getUser(): User {
        return userCollectionRef.document(userid.toString())
            .get().result?.toObject(User::class.java)!!
    }

}