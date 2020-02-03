package com.example.notes.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.dao.JoinNoteTagDAO
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.dao.TagDatabaseDao
import java.lang.IllegalArgumentException

class NoteMainListViewModelFactory(
    private val dataSource: NoteDatabaseDAO,
    private val tagSource: TagDatabaseDao,
    private val joinSource: JoinNoteTagDAO
) : ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteMainListViewModel::class.java)) {
            return NoteMainListViewModel(dataSource, tagSource, joinSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
