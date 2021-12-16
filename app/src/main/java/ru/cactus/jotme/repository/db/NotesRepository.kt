package ru.cactus.jotme.repository.db

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.entity.Note

class NotesRepository(private val db: AppDatabase) {

    fun updateInsert(note: Note): Flow<Unit> = flow {
        try {
            val response = db.getNotes().insertUpdateNote(note)
            emit(response)
        } catch (error: Throwable) {
            throw Exception(error)
        }
    }.flowOn(IO)

    fun getAll(): Flow<List<Note>> {
        try {
            return db.getNotes().gelAll()
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    fun getNote(id: Int): Flow<Note> {
        try {
            return db.getNotes().getNote(id)
        } catch (e: Exception) {
            throw Exception(e)
        }
    }


    fun delete(id: Int): Flow<Unit> = flow {
        try {
            val response = db.getNotes().deleteNote(id)
            emit(response)
        } catch (error: Throwable) {
            throw Exception(error)
        }
    }.flowOn(IO)
}
//class NotesRepository(private val db: AppDatabase) {
//
//    fun updateInsert(note: Note) = db.getNotes().insertUpdateNote(note)
//
//    fun getAll(): List<Note> = db.getNotes().gelAll()
//
//    fun getNote(id:Int): Note = db.getNotes().getNote(id)
//
//    fun delete(note: Note) = db.getNotes().deleteNote(note)
//
//}