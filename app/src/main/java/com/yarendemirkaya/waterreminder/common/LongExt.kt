package com.yarendemirkaya.waterreminder.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedTime(pattern: String = "HH:mm"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
}