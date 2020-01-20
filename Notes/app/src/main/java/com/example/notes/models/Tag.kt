package com.example.notes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey



@Entity(
    tableName = "tags_table",
    indices = [
        Index(value = ["title_tag"], unique = true)
    ]
)
data class Tag (

    @PrimaryKey(autoGenerate = true)
    var tagId: Long = 0L,

    @ColumnInfo(name = "title_tag")
    var title: String = ""
)