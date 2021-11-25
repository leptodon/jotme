package ru.cactus.jotme.ui.note

import android.content.SharedPreferences
import ru.cactus.jotme.repository.entity.Note

interface NoteContract {
    interface View {
        fun updateViewData()
        fun showSaveToast()
        fun shareNote(note: Note)
    }

    interface Presenter {
        fun addNewNote(sharedPref: SharedPreferences, title: String, body: String)
        fun getAllNotes(sharedPref: SharedPreferences): List<Note>
        fun shareNote(note:Note)
    }

    interface Model {
        fun getNote(id: Int): Note
        fun getAllNote(sharedPref: SharedPreferences): List<Note>
        fun saveNote(sharedPref: SharedPreferences, note: Note)
        fun updateNote(note: Note): Note
        fun deleteNote(id: Int)
    }
}