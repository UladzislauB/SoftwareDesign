package com.example.notes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.dao.JoinNoteTagDAO
import com.example.notes.dao.TagDatabaseDao
import java.lang.IllegalArgumentException

class TagListViewModelFactory(
    private val noteId: Long,
    private val sourceTag: TagDatabaseDao,
    private val sourceJoin: JoinNoteTagDAO
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagListViewModel::class.java)) {
            return TagListViewModel(noteId, sourceTag, sourceJoin) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}