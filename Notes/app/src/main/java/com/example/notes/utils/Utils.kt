package com.example.notes.utils

import java.sql.Timestamp
import java.util.*


fun getCurrentTime(seconds: Long): String {
    val stamp = Timestamp(seconds)
    val date = Date(stamp.getTime()).toLocaleString()
    return date
}

fun getSummary(text: String): String {
    if (text.length > 100) {
        return text.take(100) + "..."
    } else {
        return text
    }
}