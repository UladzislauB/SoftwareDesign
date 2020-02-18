package com.example.yetanotherfeed.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDAO {

    @Query("SELECT * FROM databaseitem")
    fun getAllItems(): LiveData<List<DatabaseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<DatabaseItem>)

    @Query("DELETE FROM databaseitem")
    fun clearData()
}

@Database(entities = [DatabaseItem::class], version = 1)
abstract class ItemsDatabase : RoomDatabase() {
    abstract val itemDao: ItemDAO
}


private lateinit var INSTANCE: ItemsDatabase


fun getDatabase(context: Context): ItemsDatabase {
    synchronized(ItemsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ItemsDatabase::class.java,
                "videos"
            ).build()
        }
    }
    return INSTANCE
}