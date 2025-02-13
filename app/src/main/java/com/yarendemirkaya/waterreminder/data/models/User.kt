package com.yarendemirkaya.waterreminder.data.models

data class User(
    val name: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val age: Int = 0,
    val gender: String = "",
    val dailyWaterGoal: Int = 0,
    val sleepTime: String = ""
)
