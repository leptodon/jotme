package ru.cactus.jotme.contract

import ru.cactus.jotme.Note

interface ModelInterface {
    fun getNote(id: Int): Note
    fun getAllNote(): List<Note>
    fun saveNote(note: Note)
    fun updateNote(note: Note): Note
    fun deleteNote(id: Int)
}