package com.example.notes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.models.Note

@Dao
interface NoteDatabaseDAO {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("SELECT * FROM notes_table WHERE noteId = :key")
    fun get(key: Long): Note

    @Query("DELETE FROM notes_table")
    fun clear()

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table ORDER BY title")
    fun sortNotesByTitle(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table ORDER BY date_of_change_timestamp DESC")
    fun sortNotesByDateChange(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table ORDER BY noteId DESC LIMIT 1")
    fun getLastNote(): Note?

    @Query("SELECT * FROM notes_table WHERE noteId = :id")
    fun getNoteById(id: Long) : LiveData<Note>
}