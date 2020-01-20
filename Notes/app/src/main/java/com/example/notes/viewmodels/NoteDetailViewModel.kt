package com.example.notes.viewmodels

import androidx.lifecycle.ViewModel
import com.example.notes.dao.JoinNoteTagDAO
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.dao.TagDatabaseDao
import com.example.notes.getCurrentTime
import kotlinx.coroutines.*

class NoteDetailViewModel(
    private val noteId: Long,
    notesSource: NoteDatabaseDAO,
    tagsSource: TagDatabaseDao,
    joinNoteTagsSource: JoinNoteTagDAO
) : ViewModel() {

    val sourceNotes = notesSource
    val sourceTags = tagsSource
    val sourceJoinNoteTag = joinNoteTagsSource


    // Coroutines variables
    private val viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun onSaveChanges(title: String, body: String, color_number: Int) {
        uiScope.launch {
            save(title, body, color_number)
        }
    }


    private suspend fun save(title: String, body: String, color_number: Int) {
        withContext(Dispatchers.IO) {
            var note = sourceNotes.get(noteId)
            if (note.title != title || note.body != body || note.color_number != color_number) {
                note.title = title
                note.body = body
                note.color_number = color_number
                note.change_date = getCurrentTime()
                sourceNotes.update(note)
            }

        }

    }
}