package com.yarendemirkaya.waterreminder.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class WaterDataSource @Inject constructor(private val fireStore: FirebaseFirestore) {


    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val userId = currentUser?.uid ?: ""

    suspend fun addWaterIntake( waterIntake: WaterIntake) {
        val waterIntakeRef = fireStore.collection("users")
            .document(userId)
            .collection("waterIntakes")
            .document()

        val waterIntakeData = mapOf(
            "amount" to waterIntake.amount,
            "time" to waterIntake.time
        )
        waterIntakeRef.set(waterIntakeData).await()
    }


    fun getWaterIntakes(userId: String) = flow {
        try {
            val waterCollection =
                fireStore.collection("users").document(userId).collection("waterIntake")
            val waterIntakes = waterCollection.get().await().toObjects(WaterIntake::class.java)
            emit(Resource.Success(waterIntakes))
        } catch (e: Exception) {
            emit(Resource.Error("Su kayıtları getirilirken hata oluştu: ${e.message}"))
        }
    }

    fun deleteWaterIntake(userId: String, waterIntakeId: String) = flow {
        try {
            val waterCollection =
                fireStore.collection("users").document(userId).collection("waterIntake")
            waterCollection.document(waterIntakeId).delete().await()
            emit(Resource.Success("Su kaydı silindi"))
        } catch (e: Exception) {
            emit(Resource.Error("Su kaydı silinirken hata oluştu: ${e.message}"))
        }
    }
}