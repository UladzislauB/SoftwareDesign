package com.example.notes.models

import androidx.room.*


@Entity(
    tableName = "join_note_tag",
    indices = [Index("noteId"), Index("tagId")],
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = ["noteId"],
            childColumns = ["noteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["tagId"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class JoinNoteTag(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "noteId")
    var noteId: Long = 0L,

    @ColumnInfo(name = "tagId")
    var tagId: Long = 0L
)