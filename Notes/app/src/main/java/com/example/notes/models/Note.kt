package com.example.notes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Entity(tableName = "notes_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("DD MMM YYYY HH:mm")),

    @ColumnInfo(name = "body")
    var body: String = "",

    @ColumnInfo(name = "date_of_change")
    var change_date: String = title

)