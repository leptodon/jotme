package ru.cactus.jotme.ui.note

import android.content.SharedPreferences
import ru.cactus.jotme.repository.entity.Note

class NotePresenter(
    private val view: NoteContract.View
) : NoteContract.Presenter {
    private var model: NoteContract.Model = NoteModel()

    /**
     * Сохраняем заметку в shared preferences
     * @param sharedPref передаем из активити
     * @param title текст заголовка заметки
     * @param body основной текст заметки
     */
    override fun addNewNote(sharedPref: SharedPreferences, title: String, body: String) {
        model.saveNote(sharedPref, Note(0, title, body))
        view.showSaveToast()
    }

    /**
     * Получаем все заметки сохраненные в shared preferences
     * @param sharedPref передаем из активити
     */
    override fun getAllNotes(sharedPref: SharedPreferences): List<Note> {
        return model.getAllNote(sharedPref)
    }

    /**
     * Вызываем intent для передачи заметки в другое приложение
     */
    override fun shareNote(note: Note) {
        view.shareNote(note)
    }
}