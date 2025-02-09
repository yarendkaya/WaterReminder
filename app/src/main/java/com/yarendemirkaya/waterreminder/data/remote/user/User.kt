package com.yarendemirkaya.waterreminder.data.remote.user

data class User(
    val id: String = "", // Firebase Authentication UID
    val name: String = "",
    val height: Int = 0, // cm
    val weight: Int = 0, // kg
    val age: Int = 0,
    val gender: String = "", // "Male", "Female", vb.
    val dailyWaterGoal: Int = 0, // ml cinsinden günlük hedef
    val sleepTime: String = "" // HH:mm formatında olabilir
)
