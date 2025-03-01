package com.yarendemirkaya.waterreminder.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.data.models.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserDataSource @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

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

    fun getUserData(userId: String): Flow<User?> = callbackFlow {
        val docRef = fireStore.collection("users").document(userId)

        val listener = docRef.addSnapshotListener { snapshot, _ ->
            if (snapshot != null && snapshot.exists()) {
                val user = snapshot.toObject(User::class.java)
                trySend(user)
            } else {
                trySend(null)
            }
        }
        awaitClose { listener.remove() }
    }


    suspend fun checkUserHasData(userId: String): Resource<Boolean> {
        return try {
            val result = fireStore.collection("isAddedInfo").document(userId).get().await()
                .toObject(Boolean::class.java)
            if (result != null) {
                Resource.Success(result)
            } else {
                Resource.Error("Error checking user data")
            }
        } catch (e: Exception) {
            Resource.Error("Error checking user data")
        }
    }
}