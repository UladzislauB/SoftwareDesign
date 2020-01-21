package com.example.notes.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.notes.models.JoinNoteTag
import com.example.notes.models.Note
import com.example.notes.models.Tag


@Dao
interface JoinNoteTagDAO {

    @Insert
    fun insert(joinNoteTag: JoinNoteTag)

    @Delete
    fun delete(joinNoteTag: JoinNoteTag)


    @Query(
        "SELECT notes_table.noteId, title, body, date_of_change_timestamp, color_number " +
                "FROM notes_table " +
                "JOIN join_note_tag ON notes_table.noteId = join_note_tag.noteId " +
                "WHERE join_note_tag.tagId = :tagId"
    )
    fun getNotesForTag(tagId: Long): LiveData<List<Note>>


    @Query(
        "SELECT tags_table.tagId, tags_table.title_tag " +
                "FROM tags_table J" +
                "OIN join_note_tag ON join_note_tag.tagId = tags_table.tagId " +
                "WHERE join_note_tag.noteId = :noteId"
    )
    fun getTagsForNote(noteId: Long): LiveData<List<Tag>>
}