package ru.cactus.jotme.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.cactus.jotme.data.repository.db.NotesDao
import ru.cactus.jotme.data.repository.db.entity.DbNote


/**
 * Класс билдер объекта БД
 */
@Database(entities = [DbNote::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Получаем заметки из БД
     */
    abstract fun getNotesDao(): NotesDao

    companion object {

        @Volatile
        private var instances: AppDatabase? = null

        /**
         * Получение инстанса объекта БД
         */
        fun getInstance(context: Context): AppDatabase {
            return instances ?: synchronized(this)
            {
                buildDatabase(context).also { instances = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "notes_db.db"
            ).build()
    }
}