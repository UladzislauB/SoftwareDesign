package com.example.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.dao.JoinNoteTagDAO
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.dao.TagDatabaseDao
import com.example.notes.models.Note
import com.example.notes.utils.getCurrentTime
import kotlinx.coroutines.*
import java.sql.Timestamp

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
    private lateinit var note: Note

    // Coroutines variables
    private val viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _isJustCreated = MutableLiveData<Boolean>()
    val isJustCreated: LiveData<Boolean>
        get() = _isJustCreated

    init {
        instantiatingNote()
        _isJustCreated.value = isJustCreated
    }

    fun instantiatingNote() {
        uiScope.launch {
            note = getNote()
        }
    }

    private suspend fun getNote() : Note {
        return withContext(Dispatchers.IO) {
            sourceNotes.get(noteId)
        }
    }

    fun onSaveChanges(title: String, body: String, color_number: Int) {
        uiScope.launch {
            save(title, body, color_number)
        }
    }


    private suspend fun save(title: String, body: String, color_number: Int) {
        withContext(Dispatchers.IO) {
            if (note.title != title || note.body != body || note.color_number != color_number) {
                if (title != "") {
                    note.title = title
                }
                note.body = body
                note.color_number = color_number
                note.change_date_stamp = System.currentTimeMillis()
                sourceNotes.update(note)
            }

        }

    }


    fun getTitle() : String {
        return note.title
    }

    fun getBody() : String {
        return note.body
    }

    fun getColor(): Int {
        return note.color_number
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}