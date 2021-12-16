package ru.cactus.jotme.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.cactus.jotme.repository.db.NotesDao
import ru.cactus.jotme.repository.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun getNotes():NotesDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock){
            instance ?: createdDatabase(context).also { articleDataBase ->
                instance = articleDataBase
            }
        }

        private fun createdDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "notes_db.db"
            ).build()
    }
}