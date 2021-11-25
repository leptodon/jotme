package ru.cactus.jotme.ui.note

import android.content.SharedPreferences
import ru.cactus.jotme.repository.entity.Note

class NotePresenter(
    private val view: NoteContract.View
) : NoteContract.Presenter {
    private var model: NoteContract.Model = NoteModel()

    override fun addNewNote(sharedPref: SharedPreferences, title: String, body: String) {
        model.saveNote(sharedPref, Note(0, title, body))
        view.showSaveToast()
    }

    override fun getAllNotes(sharedPref: SharedPreferences): List<Note> {
        return model.getAllNote(sharedPref)
    }

    override fun shareNote(note: Note) {
        view.shareNote(note)
    }
}