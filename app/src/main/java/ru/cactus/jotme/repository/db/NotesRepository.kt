package ru.cactus.jotme.repository.db

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.entity.Note

class NotesRepository(private val db: AppDatabase) {

    fun updateInsert(note: Note): Flow<Unit> = db.getNotesDao().insertUpdateNote(note)

    fun getAll(): Flow<List<Note>> {
            return db.getNotesDao().gelAll()
    }

    fun getNote(id: Int): Flow<Note> {
            return db.getNotesDao().getNote(id)
    }

    fun delete(id: Int): Flow<Unit> = flow {
            val response = db.getNotesDao().deleteNote(id)
            emit(response)
    }.flowOn(IO)
}