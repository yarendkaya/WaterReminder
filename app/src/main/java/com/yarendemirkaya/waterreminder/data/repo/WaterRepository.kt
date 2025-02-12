package com.yarendemirkaya.waterreminder.data.repo

import com.yarendemirkaya.waterreminder.common.Resource
import com.yarendemirkaya.waterreminder.data.datasource.WaterDataSource
import com.yarendemirkaya.waterreminder.data.models.WaterIntake
import javax.inject.Inject

class WaterRepository @Inject constructor(private val waterDataSource: WaterDataSource) {

    suspend fun addWaterIntake(water: WaterIntake) = waterDataSource.addWaterIntake(water)

    suspend fun getWaterIntakes(): Resource<List<WaterIntake>> {
        return  waterDataSource.getWaterIntakes()
    }


}