package com.yarendemirkaya.waterreminder.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.common.getWeekMillisRange
import com.yarendemirkaya.waterreminder.common.toFormattedDate

import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import kotlinx.coroutines.tasks.await
import java.util.Calendar
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

//    suspend fun getWaterIntakes(): Resource<List<WaterIntake>> {
//        return try {
//            val collectionRef = getWaterIntakeCollection()
//                ?: return Resource.Error("User not logged in")
//
//            val snapshot = collectionRef.get().await()
//            val waterIntakes = snapshot.toObjects(WaterIntake::class.java)
//
//            Resource.Success(waterIntakes)
//        } catch (e: Exception) {
//            Resource.Error(e.localizedMessage ?: "Failed to fetch water intakes.")
//        }
//    }

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

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)

            calendar.set(year, month, 1, 0, 0, 0)
            val startOfMonth = calendar.timeInMillis

            calendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59)
            val endOfMonth = calendar.timeInMillis

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

fun getTodayMillisRange(): Pair<Long, Long> {
    val calendar = Calendar.getInstance()


    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startOfDay = calendar.timeInMillis


    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    val endOfDay = calendar.timeInMillis

    return Pair(startOfDay, endOfDay)
}




