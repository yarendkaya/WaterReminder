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
    auth: FirebaseAuth
) {

    private val currentUser = auth.currentUser
    suspend fun saveUserInfo(user: User) {
        if (currentUser != null) {
            try {
                fireStore.collection("users").document(currentUser.uid).set(user).await()
            } catch (e: Exception) {
                println(e.localizedMessage ?: "Failed to save user info")
            }


            //burası ayrılacak daha sonra
            try {
                fireStore.collection("isAddedInfo").document(currentUser.uid).set(
                    mapOf("isAddedInfo" to true)
                ).await()
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

            val isAddedInfo = result.getBoolean("isAddedInfo")

            Resource.Success(isAddedInfo ?: false)
        } catch (e: Exception) {
            println(e.localizedMessage ?: "Failed to check user data")
            Resource.Error("Error checking user data")
        }
    }

    suspend fun updateUserData(user: User): Resource<Boolean> {
        val db = FirebaseFirestore.getInstance()
        val userRef = currentUser?.let { db.collection("users").document(it.uid) }

        val userMap = mapOf(
            "name" to user.name,
            "age" to user.age,
            "height" to user.height,
            "weight" to user.weight,
            "gender" to user.gender,
            "dailyWaterGoal" to user.dailyWaterGoal,
            "sleepTime" to user.sleepTime
        )
        return try {
            userRef?.update(userMap)?.await()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update user data")
        }
    }

    suspend fun getUserName(): Resource<String> {
       return try {
           val result = fireStore.collection("users").document(currentUser?.uid!!).get().await()
           val name = result.getString("name")
           Resource.Success(name ?: "")
       } catch (e: Exception) {
           Resource.Error(e.localizedMessage ?: "Failed to get user name")
       }
    }
}