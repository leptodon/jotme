package ru.cactus.jotme.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.cactus.jotme.repository.db.entity.Note


/**
 * Интерфейс для работы с БД основные CRUD операции
 */
@Dao
interface NotesDao {

    /**
     * Сохранение заметки в БД
     * @param note объект заметки
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateNote(note: Note)

    /**
     * Получение списка заметок из БД
     */
    @Query("SELECT * FROM notes")
    suspend fun gelAll(): List<Note>

    /**
     * Удаление заметки из БД
     */
    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNote(id: Int)
}