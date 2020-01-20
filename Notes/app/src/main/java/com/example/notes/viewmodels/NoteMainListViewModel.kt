package com.example.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.models.Note
import kotlinx.coroutines.*


class NoteMainListViewModel(
    dataSource: NoteDatabaseDAO
) : ViewModel() {

    val database = dataSource


    // Coroutine variables
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val notes = database.getAllNotes()


    private val _navigateToNoteDetail = MutableLiveData<Note>()
    val navigateToNoteDetail: LiveData<Note>
        get() = _navigateToNoteDetail


    // Call after navigating  to NoteDetailFragment
    fun doneNavigating() {
        _navigateToNoteDetail.value = null
    }

    // suspended functions
    private suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            database.insert(note)
        }
    }


    fun onCreate() {
        uiScope.launch {
            val note = Note()
            insert(note)

            // Navigating to NoteDetailFragment
            _navigateToNoteDetail.value = note
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}