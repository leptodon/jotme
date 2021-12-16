package ru.cactus.jotme.repository.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.cactus.jotme.repository.entity.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpdateNote(note: Note)

    @Query("Select * from notes")
//    fun gelAll(): Flow<List<Note>>
    fun gelAll(): List<Note>

    @Query("SELECT * FROM notes WHERE id LIKE :id LIMIT 1")
    fun getNote(id:Int): Note

    @Delete
    fun deleteNote(note: Note)
}