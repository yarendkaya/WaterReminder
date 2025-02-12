package com.yarendemirkaya.waterreminder.data.models

data class WaterIntake(
    val amount: Int = 0, // ml cinsinden alınan su miktarı
    val time: String = "" // HH:mm formatında su içilen saat
)
