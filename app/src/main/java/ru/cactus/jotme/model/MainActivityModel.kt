package ru.cactus.jotme.model

import ru.cactus.jotme.Note
import ru.cactus.jotme.contract.ModelInterface

class MainActivityModel : ModelInterface {

    override fun getNote(id: Int): Note {
        val lNote = Note(1,"Title", "Body text")
        return lNote
    }

    override fun getAllNote(): List<Note> {
        val lNote = Note(1,"Title", "Body text")
        val list = mutableListOf<Note>()
        list.add(lNote)
        return list
    }

    override fun saveNote(note: Note) {
        TODO("Not yet implemented")
    }

    override fun updateNote(note: Note): Note {
        val lNote = Note( 1,"Title", "Body text")
        return lNote
    }

    override fun deleteNote(id: Int) {
        TODO("Not yet implemented")
    }

}