package com.yarendemirkaya.waterreminder.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.common.toFormattedDate
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class WaterDataSource @Inject constructor(private val fireStore: FirebaseFirestore) {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val userId = currentUser?.uid ?: ""

    private val waterIntakeRef = fireStore.collection("users")
        .document(userId)
        .collection("waterIntakes")
        .document()

    suspend fun addWaterIntake(waterIntake: WaterIntake) {
        val waterIntakeData = mapOf(
            "amount" to waterIntake.amount,
            "time" to waterIntake.time
        )
        waterIntakeRef.set(waterIntakeData).await()
    }

    suspend fun getWaterIntakes(): Resource<List<WaterIntake>> {
        return try {
            val waterIntakes = fireStore.collection("users")
                .document(userId)
                .collection("waterIntakes")
                .get()
                .await()
                .toObjects(WaterIntake::class.java)
            Resource.Success(waterIntakes)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Error fetching water intakes")
        }
    }
}



