package com.example.notes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.dao.JoinNoteTagDAO
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.dao.TagDatabaseDao
import java.lang.IllegalArgumentException

class NoteDetailViewModelFactory (
    private val noteId: Long,
    private val notesSource: NoteDatabaseDAO,
    private val tagsSource: TagDatabaseDao,
    private val joinNoteTagsSource: JoinNoteTagDAO
) : ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteDetailViewModel::class.java)) {
            return NoteDetailViewModel(noteId, notesSource, tagsSource, joinNoteTagsSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}