package com.example.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.notes.adapters.NoteComparators
import com.example.notes.dao.JoinNoteTagDAO
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.dao.TagDatabaseDao
import com.example.notes.models.Note
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList


class NoteMainListViewModel(
    dataSource: NoteDatabaseDAO,
    tagSource: TagDatabaseDao,
    joinSource: JoinNoteTagDAO
) : ViewModel() {

    val database = dataSource
    val tagDatabase = tagSource
    val joinDatabase = joinSource

    // Coroutine variables
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var notes = database.getAllNotes()
    var notesFiltered = MutableLiveData<List<Note>>()

    // Use notesFiltered or notes
    var startSearch: Boolean = false

    var tags = tagDatabase.getAllTagsByTitle()

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
    }

    fun onNoteClicked(noteId: Long) {
        _navigateToNoteDetail.value = noteId
        tappedCreate = false
    }


    // suspended functions
    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
            joinDatabase.clear()
        }
    }

    private suspend fun getNotesForTag(tagId: Long) : List<Note> {
        return withContext(Dispatchers.IO) {
            joinDatabase.getNotesForTag(tagId)
        }
    }

    private suspend fun getAllNotes() : LiveData<List<Note>> {
        return withContext(Dispatchers.IO) {
            database.getAllNotes()
        }
    }

    fun onTagsQueryChange(tagId: Long){
        if (tagId == 0L)
            uiScope.launch {
                notesFiltered.value = notes.value
            }
        else
            uiScope.launch {
                notesFiltered.value = getNotesForTag(tagId)
            }
        startSearch = true
    }

    fun onCreate() {
        uiScope.launch {
            tappedCreate = true
            // Navigating to NoteDetailFragment
            _navigateToNoteDetail.value = 0L
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