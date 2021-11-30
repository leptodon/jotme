package ru.cactus.jotme.ui.note

import android.content.SharedPreferences
import ru.cactus.jotme.repository.entity.Note

class NotePresenter(
    private val sharedPref: SharedPreferences,
    private val view: NoteContract.View
) : NoteContract.Presenter {
    private var model: NoteContract.Model = NoteModel()

    /**
     * Сохраняем заметку в shared preferences
     * @param sharedPref передаем из активити
     * @param title текст заголовка заметки
     * @param body основной текст заметки
     */
    override fun addNewNote(title: String, body: String) {
        if (title.isNotEmpty()) {
            model.saveNote(sharedPref, Note(0, title, body))
            view.showSaveToast()
        } else {
            deleteNote(0)
        }

    }

    /**
     * Получаем все заметки сохраненные в shared preferences
     * @param sharedPref передаем из активити
     */
    override fun getAllNotes(): List<Note> {
        return model.getAllNote(sharedPref)
    }

    /**
     * Вызываем intent для передачи заметки в другое приложение
     */
    override fun shareNote(note: Note) {
        view.shareNote(note)
    }

    /**
     * Удаление заметки
     */
    override fun deleteNote(id: Int) {
        model.deleteNote(sharedPref, 0)
        view.showDeleteToast()
    }

    /**
     * Проверяем есть ли сохраненная заметка
     */
    override fun checkNote(): Boolean {
        return model.getAllNote(sharedPref).isNullOrEmpty()
    }

}