package ru.cactus.jotme.ui.note

import android.content.SharedPreferences
import ru.cactus.jotme.repository.entity.Note

interface NoteContract {
    interface View {
        fun showSaveToast()
        fun showDeleteToast()
        fun shareNote(note: Note)
    }

    interface Presenter {
        fun addNewNote(title: String, body: String)
        fun getAllNotes(): List<Note>
        fun shareNote(note:Note)
        fun deleteNote(id: Int)
        fun checkNote():Boolean
    }

    interface Model {
        fun getAllNote(sharedPref: SharedPreferences): List<Note>
        fun saveNote(sharedPref: SharedPreferences, note: Note)
        fun updateNote(sharedPref: SharedPreferences, note: Note): Note
        fun deleteNote(sharedPref: SharedPreferences, id: Int)
    }
}