package ru.cactus.jotme.ui.note

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.cactus.jotme.repository.entity.Note

class NoteModel : NoteContract.Model {

    override fun getNote(id: Int): Note {
        val lNote = Note(1, "Title", "Body text")
        return lNote
    }

    override fun getAllNote(sharedPref: SharedPreferences): List<Note> {
        val title = sharedPref.getString("NOTE_TITLE", "New note") ?: "New note"
        val body = sharedPref.getString("NOTE_BODY", "Empty body") ?: "Empty body"
        val list = mutableListOf<Note>()
        list.add(Note(1, title, body))
        return list
    }

    override fun saveNote(sharedPref: SharedPreferences, note: Note) {
        with(sharedPref.edit()) {
            putString("NOTE_TITLE", note.title)
            putString("NOTE_BODY", note.body)
            apply()
        }
    }

    override fun updateNote(note: Note): Note {
        val lNote = Note(1, "Title", "Body text")
        return lNote
    }

    override fun deleteNote(id: Int) {
        TODO("Not yet implemented")
    }

}