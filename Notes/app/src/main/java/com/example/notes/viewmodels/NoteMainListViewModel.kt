package com.example.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.adapters.NoteComparators
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.models.Note
import kotlinx.coroutines.*
import java.util.*


class NoteMainListViewModel(
    dataSource: NoteDatabaseDAO
) : ViewModel() {

    val database = dataSource

    // Coroutine variables
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var notes: LiveData<List<Note>>
    private var lastNote = MutableLiveData<Note?>()


    private val _navigateToNoteDetail = MutableLiveData<Long>()
    val navigateToNoteDetail: LiveData<Long>
        get() = _navigateToNoteDetail

    // Call after navigating  to NoteDetailFragment
    fun doneNavigating() {
        _navigateToNoteDetail.value = null
    }

    var tappedCreate: Boolean = false

    fun finishCreateTapping() {
        tappedCreate = false
        val i = 0
    }

    fun onNoteClicked(noteId: Long) {
        _navigateToNoteDetail.value = noteId
        tappedCreate = false
    }

    init {
        notes = database.getAllNotes()
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
            tappedCreate = true
            // Navigating to NoteDetailFragment
            _navigateToNoteDetail.value = lastNote.value?.noteId
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
        }
    }

    fun onSortByTitle() {
        Collections.sort(notes.value!!, NoteComparators.TitleComparator())
    }

    fun onSortByDateChange() {
        Collections.sort(notes.value!!, NoteComparators.DateChangeComparator())
    }

    fun onNormalOrder() {
        Collections.sort(notes.value!!, NoteComparators.IdComparator())
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}