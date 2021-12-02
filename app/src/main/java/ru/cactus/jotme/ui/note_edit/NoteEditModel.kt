package ru.cactus.jotme.ui.note_edit

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.cactus.jotme.repository.entity.Note

class NoteEditModel : NoteEditContract.Model {

    override fun getAllNote(sharedPref: SharedPreferences): List<Note> {
//        val json = sharedPref.all
//        val list = mutableListOf<Note>()
        val json = sharedPref.getString(NOTE, "")
        val list = mutableListOf<Note>()

        if (!json.isNullOrEmpty()) {
            val note = Gson().fromJson(json, Note::class.java)
            list.add(note)
//            json.forEach { list.add(Note(0, it.key, it.value.toString())) }
        }

        return list
    }

    override fun saveNote(sharedPref: SharedPreferences, note: Note) {
        val json = Gson().toJson(note)

        with(sharedPref.edit()) {
            putString(NOTE, json)
            apply()
        }
    }

    override fun updateNote(sharedPref: SharedPreferences, note: Note): Note {
        TODO(
            "Реализация будет добавлена при имплементации БД," +
                    " или при отображении нескольких заметок на главной"
        )
    }

    override fun deleteNote(sharedPref: SharedPreferences, id: Int) {
        with(sharedPref.edit()) {
            putString(NOTE, null)
            apply()
        }
    }

    companion object {
        private const val NOTE = "Note"
    }
}