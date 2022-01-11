package ru.cactus.jotme.repository.db

import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.entity.Note

/**
 * Репозиторий для работы с интерфейсом БД
 * @param db объект базы данных
 */
class DatabaseRepositoryImpl(private val db: AppDatabase) : DatabaseRepository {

    /**
     * Сохранение заметки в БД
     * @param note объект заметки
     */
    override suspend fun updateInsert(note: Note): Unit = db.getNotesDao().insertUpdateNote(note)

    /**
     * Получение списка заметок из БД
     */
    override suspend fun getAll(): List<Note> = db.getNotesDao().gelAll()

    /**
     * Удаление заметки из БД
     * @param id идентификатор удаляемой заметки
     */
    override suspend fun delete(id: Int): Unit = db.getNotesDao().deleteNote(id)
}