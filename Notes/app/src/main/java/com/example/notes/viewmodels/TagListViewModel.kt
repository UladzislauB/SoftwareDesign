package com.example.notes.viewmodels

import androidx.lifecycle.ViewModel
import com.example.notes.dao.JoinNoteTagDAO
import com.example.notes.dao.TagDatabaseDao
import com.example.notes.models.JoinNoteTag
import com.example.notes.models.Tag
import kotlinx.coroutines.*

class TagListViewModel(
    val noteId: Long,
    sourceTag: TagDatabaseDao,
    sourceJoin: JoinNoteTagDAO
) : ViewModel() {

    val tagSource = sourceTag
    val joinSource = sourceJoin

    // Coroutine variables
    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main+viewModelJob)

    var tags = tagSource.getAllTagsByTitle()

    private suspend fun insert(tag: Tag) {
        withContext(Dispatchers.IO) {
            tagSource.insert(tag)
            val join = JoinNoteTag(noteId = noteId, tagId = tag.tagId)
            joinSource.insert(join)
        }
    }

    fun onCreate(title: String) {
        uiScope.launch {
            val tag = Tag(title = title)
            insert(tag)
        }
    }
}