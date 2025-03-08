package com.yarendemirkaya.waterreminder.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yarendemirkaya.waterreminder.common.Resource

import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class WaterDataSource @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val userId: String?
        get() = auth.currentUser?.uid


    suspend fun addWaterIntake(waterIntake: WaterIntake): Resource<Unit> {
        return try {
            val userId = userId ?: return Resource.Error("User not logged in")
            val waterIntakeRef = fireStore.collection("users")
                .document(userId)
                .collection("waterIntakes")
                .document()

            val waterIntakeData = mapOf(
                "amount" to waterIntake.amount,
                "time" to waterIntake.time
            )

            waterIntakeRef.set(waterIntakeData).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error adding water intake")
        }
    }

    suspend fun getWaterIntakes(): Resource<List<WaterIntake>> {
        return try {
            val userId = userId ?: return Resource.Error("User not logged in")
            val snapshot = fireStore.collection("users")
                .document(userId)
                .collection("waterIntakes")
                .get()
                .await()

            val waterIntakes = snapshot.documents.map { document ->
                document.toObject(WaterIntake::class.java)?.copy(id = document.id)
            }.filterNotNull()

            Resource.Success(waterIntakes)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error fetching water intakes")
        }
    }

    suspend fun deleteWaterIntake(waterIntake: WaterIntake): Resource<Boolean> {
        return try {
            val userId = userId ?: return Resource.Error("User not logged in")

            if (waterIntake.id.isEmpty()) {
                return Resource.Error("Water intake ID is missing")
            }

            fireStore.collection("users")
                .document(userId)
                .collection("waterIntakes")
                .document(waterIntake.id)
                .delete()
                .await()

            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error deleting water intake")
        }
    }
}



