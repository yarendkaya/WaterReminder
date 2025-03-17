package com.yarendemirkaya.waterreminder.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.common.getMonthMillisRange
import com.yarendemirkaya.waterreminder.common.getTodayMillisRange
import com.yarendemirkaya.waterreminder.common.getWeekMillisRange
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WaterDataSource @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val userId: String?
        get() = auth.currentUser?.uid


    private fun getWaterIntakeCollection(): CollectionReference? {
        val userId = userId ?: return null
        return fireStore.collection("users")
            .document(userId)
            .collection("waterIntakes")
    }

    suspend fun addWaterIntake(waterIntake: WaterIntake): Resource<Unit> {
        return try {
            val collectionRef = getWaterIntakeCollection()
                ?: return Resource.Error("User not logged in")

            val documentRef = collectionRef.document()
            val waterIntakeWithId = waterIntake.copy(id = documentRef.id)

            documentRef.set(waterIntakeWithId).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to add water intake.")
        }
    }


    suspend fun deleteWaterIntake(waterIntake: WaterIntake): Resource<Boolean> {
        return try {
            val collectionRef = getWaterIntakeCollection()
                ?: return Resource.Error("User not logged in")

            if (waterIntake.id.isEmpty()) {
                return Resource.Error("Invalid water intake ID.")
            }

            collectionRef.document(waterIntake.id).delete().await()

            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to delete water intake.")
        }
    }

    suspend fun getTodayIntakeByTime(): Resource<List<WaterIntake>> {
        return try {
            val collectionRef = getWaterIntakeCollection()
                ?: return Resource.Error("User not logged in")

            val (startOfDay, endOfDay) = getTodayMillisRange()

            val snapshot = collectionRef.get().await()

            val waterIntakes = snapshot.toObjects(WaterIntake::class.java).filter {
                val timeLong = it.time?.toLongOrNull()
                timeLong != null && timeLong in startOfDay..endOfDay
            }
            Resource.Success(waterIntakes)

        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to fetch today's water intakes.")
        }
    }

    suspend fun getWeeklyIntakeByTime(): Resource<List<WaterIntake>> {
            return try {
                val collectionRef = getWaterIntakeCollection()
                    ?: return Resource.Error("User not logged in")

                val (startOfWeek, endOfWeek) = getWeekMillisRange()

                val snapshot = collectionRef.get().await()

                val waterIntakes = snapshot.toObjects(WaterIntake::class.java).filter {
                    val timeLong = it.time?.toLongOrNull()
                    timeLong != null && timeLong in startOfWeek..endOfWeek
                }
                Resource.Success(waterIntakes)

            } catch (e: Exception) {
                Resource.Error(e.localizedMessage ?: "Failed to fetch this week's water intakes.")
            }
        }

    suspend fun getMonthlyIntakeByTime(): Resource<List<WaterIntake>> {
        return try {
            val collectionRef = getWaterIntakeCollection()
                ?: return Resource.Error("User not logged in")

            val (startOfMonth, endOfMonth) = getMonthMillisRange()

            val snapshot = collectionRef.get().await()

            val waterIntakes = snapshot.toObjects(WaterIntake::class.java).filter {
                val timeLong = it.time?.toLongOrNull()
                timeLong != null && timeLong in startOfMonth..endOfMonth
            }

            Resource.Success(waterIntakes)

        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to fetch this month's water intakes.")
        }
    }
}






