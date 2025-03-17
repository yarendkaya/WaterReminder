package com.yarendemirkaya.waterreminder.common

import java.util.Calendar

fun getWeekMillisRange(): Pair<Long, Long> {
    val calendar = Calendar.getInstance()

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startOfWeek = calendar.timeInMillis

    calendar.add(Calendar.DAY_OF_WEEK, 6)
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    val endOfWeek = calendar.timeInMillis

    return Pair(startOfWeek, endOfWeek)
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

fun getMonthMillisRange(): Pair<Long, Long> {
    val calendar = Calendar.getInstance()

    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startOfMonth = calendar.timeInMillis

    calendar.add(Calendar.MONTH, 1)
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    val endOfMonth = calendar.timeInMillis

    return Pair(startOfMonth, endOfMonth)
}