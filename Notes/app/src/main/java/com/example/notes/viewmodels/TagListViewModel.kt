package com.example.notes.viewmodels

import androidx.lifecycle.MutableLiveData
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
    private var lastTag = MutableLiveData<Tag?>()

    private suspend fun insertTag(tag: Tag) {
        withContext(Dispatchers.IO) {
            tagSource.insert(tag)
        }
    }

    private suspend fun getLastTag(): Tag? {
        return withContext(Dispatchers.IO) {
            tagSource.getLastTag()
        }
    }

    private suspend fun insertJoin(join: JoinNoteTag) {
        withContext(Dispatchers.IO) {
            joinSource.insert(join)
        }
    }

    fun onCreate(title: String) {
        uiScope.launch {
            val tag = Tag(title = title)
            insertTag(tag)
            lastTag.value = getLastTag()
            val join = JoinNoteTag(noteId = noteId, tagId = lastTag.value!!.tagId)
            insertJoin(join)
        }
    }
}