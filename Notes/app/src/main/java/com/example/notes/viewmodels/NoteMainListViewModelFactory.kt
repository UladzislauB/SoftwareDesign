package com.example.notes.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.dao.NoteDatabaseDAO
import java.lang.IllegalArgumentException

class NoteMainListViewModelFactory(
    private val dataSource: NoteDatabaseDAO,
    private val application: Application
) : ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteMainListViewModel::class.java)) {
            return NoteMainListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
