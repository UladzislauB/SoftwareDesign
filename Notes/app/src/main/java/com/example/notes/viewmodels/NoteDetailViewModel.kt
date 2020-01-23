package com.example.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.dao.JoinNoteTagDAO
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.dao.TagDatabaseDao
import com.example.notes.models.Note
import kotlinx.coroutines.*

class NoteDetailViewModel(
    private val noteId: Long,
    isJustCreated: Boolean,
    notesSource: NoteDatabaseDAO,
    tagsSource: TagDatabaseDao,
    joinNoteTagsSource: JoinNoteTagDAO
) : ViewModel() {

    val sourceNotes = notesSource
    val sourceTags = tagsSource
    val sourceJoinNoteTag = joinNoteTagsSource

    // Note
    private var note: LiveData<Note>

    fun getNote() = note

    private var colorNumber: Int? = null

    fun setColor(value: Int) {
        colorNumber = value
    }

    // Coroutines variables
    private val viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _isJustCreated = MutableLiveData<Boolean>()
    val isJustCreated: LiveData<Boolean>
        get() = _isJustCreated

    init {
        note = sourceNotes.getNoteById(noteId)
        _isJustCreated.value = isJustCreated
    }

    fun onSaveChanges(title: String, body: String) {
        uiScope.launch {
            save(title, body)
        }
    }

    private suspend fun save(title: String, body: String) {
        withContext(Dispatchers.IO) {
            val note = sourceNotes.get(noteId)
            if (note.title != title || note.body != body || note.color_number != colorNumber) {
                if (title != "") {
                    note.title = title
                }
                note.body = body
                if (colorNumber != null) {
                    note.color_number = colorNumber as Int
                }
                note.change_date_stamp = System.currentTimeMillis()
                sourceNotes.update(note)
            }
        }

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}