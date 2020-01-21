package com.example.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.models.Note
import com.example.notes.utils.getCurrentTime
import kotlinx.coroutines.*


class NoteMainListViewModel(
    dataSource: NoteDatabaseDAO
) : ViewModel() {

    val database = dataSource

    // Coroutine variables
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val notes = database.getAllNotes()
    private var lastNote = MutableLiveData<Note?>()


    private val _navigateToNoteDetail = MutableLiveData<Note>()
    val navigateToNoteDetail: LiveData<Note>
        get() = _navigateToNoteDetail


    // Call after navigating  to NoteDetailFragment
    fun doneNavigating() {
        _navigateToNoteDetail.value = null
    }


    init {
        initializeLastNote()
    }

    private fun initializeLastNote() {
        uiScope.launch {
            lastNote.value = getLastNote()
        }
    }

    // suspended functions
    private suspend fun getLastNote(): Note? {
        return withContext(Dispatchers.IO) {
            database.getLastNote()
        }
    }


    private suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            database.insert(note)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }


    fun onCreate() {
        uiScope.launch {
            val note = Note()
            insert(note)
            lastNote.value = getLastNote()
            // Navigating to NoteDetailFragment
            _navigateToNoteDetail.value = lastNote.value?:return@launch
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}