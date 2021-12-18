package ru.cactus.jotme.repository.db

import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.entity.Note

/**
 * Репозиторий для работы с интерфейсом БД
 */
class NotesRepository(private val db: AppDatabase) {

    /**
     * Сохранение заметки в БД
     * @param note объект заметки
     */
    suspend fun updateInsert(note: Note) = db.getNotesDao().insertUpdateNote(note)

    /**
     * Получение списка заметок из БД
     */
    suspend fun getAll():List<Note> = db.getNotesDao().gelAll()

    /**
     * Удаление заметки из БД
     */
    suspend fun delete(id: Int) = db.getNotesDao().deleteNote(id)
}