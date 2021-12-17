package ru.cactus.jotme.repository.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.cactus.jotme.repository.entity.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateNote(note: Note)

    @Query("SELECT * FROM notes")
    fun gelAll(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id LIKE :id LIMIT 1")
    fun getNote(id:Int): Flow<Note>

    @Query("DELETE FROM notes WHERE id = :id")
    fun deleteNote(id: Int)
}