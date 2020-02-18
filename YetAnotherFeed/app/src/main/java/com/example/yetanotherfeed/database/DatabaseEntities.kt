package com.example.yetanotherfeed.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yetanotherfeed.models.Item


@Entity
data class DatabaseItem(

    @PrimaryKey
    val guid: String,

    val pubDate: String,

    val title: String,

    val content: String,

    val link: String
)


fun List<DatabaseItem>.asDomainModel(): List<Item> {
    return map {
        Item (
            guid = it.guid,
            pubDate = it.pubDate,
            title = it.title,
            content = it.content,
            link = it.link,
            author = "",
            description = "",
            thumbnail = "",
            categories = ArrayList(),
            enclosure = ""
        )
    }
}