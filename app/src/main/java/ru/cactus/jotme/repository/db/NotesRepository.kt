package ru.cactus.jotme.repository.db

import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.entity.Note

class NotesRepository(private val db: AppDatabase) {

    fun updateInsert(note: Note) = db.getNotes().insertUpdateNote(note)

    fun getAll(): List<Note> = db.getNotes().gelAll()

    fun getNote(id:Int): Note = db.getNotes().getNote(id)

    fun delete(note: Note) = db.getNotes().deleteNote(note)

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