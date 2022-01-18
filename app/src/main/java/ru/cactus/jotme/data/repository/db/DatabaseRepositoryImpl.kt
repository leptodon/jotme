package ru.cactus.jotme.data.repository.db

import ru.cactus.jotme.data.repository.AppDatabase
import ru.cactus.jotme.data.repository.db.entity.DbNote

/**
 * Репозиторий для работы с интерфейсом БД
 * @param db объект базы данных
 */
class DatabaseRepositoryImpl(private val db: AppDatabase) : DatabaseRepository {

    /**
     * Сохранение заметки в БД
     * @param dbNote объект заметки
     */
    override suspend fun updateInsert(dbNote: DbNote): Unit = db.getNotesDao().insertUpdateNote(dbNote)

    /**
     * Получение списка заметок из БД
     */
    override suspend fun getAll(): List<DbNote> = db.getNotesDao().gelAll()

    /**
     * Удаление заметки из БД
     * @param id идентификатор удаляемой заметки
     */
    override suspend fun delete(id: Int): Unit = db.getNotesDao().deleteNote(id)
}