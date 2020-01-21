package com.example.notes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.utils.getCurrentTime


@Entity(tableName = "notes_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,

    @ColumnInfo(name = "date_of_change_timestamp")
    var change_date_stamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "title")
    var title: String = getCurrentTime(change_date_stamp),

    @ColumnInfo(name = "body")
    var body: String = "",

    @ColumnInfo(name = "color_number")
    var color_number: Int = 0
)