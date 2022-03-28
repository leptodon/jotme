package ru.cactus.jotme.data.repository.db

import ru.cactus.jotme.data.fromDbModelConverter
import ru.cactus.jotme.data.repository.AppDatabase
import ru.cactus.jotme.data.toDatabaseModelConverter
import ru.cactus.jotme.domain.entity.Note

/**
 * Репозиторий для работы с интерфейсом БД
 * @param db объект базы данных
 */
class DatabaseRepositoryImpl(private val db: AppDatabase) :
    DatabaseRepository {

    /**
     * Сохранение заметки в БД
     * @param note объект заметки
     */
    override suspend fun updateInsert(note: Note): Unit =
        db.getNotesDao().insertUpdateNote(toDatabaseModelConverter(note))

    /**
     * Получение списка заметок из БД
     */
    override suspend fun getAll(): List<Note> =
        db.getNotesDao().gelAll().map { fromDbModelConverter(it) }

    /**
     * Удаление заметки из БД
     * @param id идентификатор удаляемой заметки
     */
    override suspend fun delete(id: Int): Unit = db.getNotesDao().deleteNote(id)
}