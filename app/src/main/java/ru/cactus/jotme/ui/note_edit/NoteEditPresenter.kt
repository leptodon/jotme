package ru.cactus.jotme.ui.note_edit

import android.content.SharedPreferences
import ru.cactus.jotme.repository.entity.Note

class NoteEditPresenter(
    private val sharedPref: SharedPreferences,
    private val view: NoteEditContract.View
) : NoteEditContract.Presenter {
    private var model: NoteEditContract.Model = NoteEditModel()
    private lateinit var note: Note

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


    /**
     * Сохранение объекта Note из intent в MainActivity
     */
    override fun saveIntent(note: Note) {
        this.note = note
    }

    /**
     * Отдаем сохраненный Note
     */
    override fun getNote(): Note = note


}