package com.yarendemirkaya.waterreminder.data.remote.water

data class Water(
    val amount: Int = 0, // ml cinsinden alınan su miktarı
    val time: String = "" // HH:mm formatında su içilen saat
)
