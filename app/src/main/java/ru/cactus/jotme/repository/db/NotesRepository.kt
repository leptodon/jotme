package ru.cactus.jotme.repository.db

import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.entity.Note

/**
 * Репозиторий для работы с интерфейсом БД
 * @param db объект базы данных
 */
class NotesRepository(private val db: AppDatabase) {

    /**
     * Сохранение заметки в БД
     * @param note объект заметки
     */
    suspend fun updateInsert(note: Note): Unit = db.getNotesDao().insertUpdateNote(note)

    /**
     * Получение списка заметок из БД
     */
    suspend fun getAll(): List<Note> = db.getNotesDao().gelAll()

    /**
     * Удаление заметки из БД
     * @param id идентификатор удаляемой заметки
     */
    suspend fun delete(id: Int): Unit = db.getNotesDao().deleteNote(id)
}