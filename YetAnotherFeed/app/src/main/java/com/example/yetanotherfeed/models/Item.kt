package com.example.yetanotherfeed.models

import java.text.DateFormat
import java.text.SimpleDateFormat

data class Item (
    val title: String,
    val pubDate: String,
    val link: String,
    val guid: String,
    val author: String,
    val thumbnail: String,
    val description: String,
    val content: String,
    val enclosure: Any,
    val categories: List<String>
)