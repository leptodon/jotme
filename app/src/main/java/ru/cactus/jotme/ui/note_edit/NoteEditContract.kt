package ru.cactus.jotme.ui.note_edit

import android.content.SharedPreferences
import ru.cactus.jotme.repository.entity.Note

interface NoteEditContract {
    interface View {
        fun showSaveToast()
        fun showDeleteToast()
        fun shareNote(note: Note?)
    }

    interface Presenter {
        fun addNewNote(id: Int?, title: String, body: String)
        fun shareNote(note: Note)
        fun deleteNote(id: Int)
    }
}