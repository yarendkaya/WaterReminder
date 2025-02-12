package com.yarendemirkaya.waterreminder.data.repo

import com.yarendemirkaya.waterreminder.data.datasource.WaterDataSource
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import javax.inject.Inject

class WaterRepository @Inject constructor(private val waterDataSource: WaterDataSource) {

    suspend fun addWaterIntake(water: WaterIntake) = waterDataSource.addWaterIntake(water)

    fun getWaterIntakes(userId: String) = waterDataSource.getWaterIntakes(userId)

    fun deleteWaterIntake(userId: String, waterIntakeId: String) =
        waterDataSource.deleteWaterIntake(userId, waterIntakeId)
}