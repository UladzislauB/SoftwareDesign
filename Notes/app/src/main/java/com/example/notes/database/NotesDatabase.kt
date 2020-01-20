package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.dao.JoinNoteTagDAO
import com.example.notes.dao.NoteDatabaseDAO
import com.example.notes.dao.TagDatabaseDao
import com.example.notes.models.JoinNoteTag
import com.example.notes.models.Note
import com.example.notes.models.Tag


@Database(
    entities = [Note::class, Tag::class, JoinNoteTag::class],
    version = 3,
    exportSchema = false
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val noteDatabaseDAO: NoteDatabaseDAO

    abstract val tagDatabaseDao: TagDatabaseDao

    abstract val joinNoteTagDAO: JoinNoteTagDAO

    companion object {

        private var INSTANCE: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notes_database"
                    ).
                        fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}