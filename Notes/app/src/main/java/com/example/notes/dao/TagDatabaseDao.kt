package com.example.notes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.models.Tag


@Dao
interface TagDatabaseDao {

    @Insert
    fun insert(tag: Tag)

    @Update
    fun update(tag: Tag)

    @Delete
    fun delete(tag: Tag)

    @Query("SELECT * FROM tags_table ORDER BY title_tag")
    fun getAllTagsByTitle(): LiveData<List<Tag>>

    @Query("SELECT * FROM tags_table ORDER BY tagId DESC LIMIT 1")
    fun getLastTag(): Tag?
}