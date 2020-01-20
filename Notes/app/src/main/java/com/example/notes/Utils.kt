package com.example.notes

import java.sql.Timestamp
import java.util.*



fun getCurrentTime() : String {
    val stamp = Timestamp(System.currentTimeMillis())
    val date = Date(stamp.getTime()).toLocaleString()
    return date
}

