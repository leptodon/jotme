package ru.cactus.jotme.ui.preview

import ru.cactus.jotme.repository.entity.Note

class PreviewPresenter(private val view: PreviewContract.View) : PreviewContract.Presenter {
    private lateinit var note: Note

    /**
     * Открытие экрана редактирования заметки
     */
    override fun onEditNoteClick() {
        view.startEditNoteActivity()
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
    override fun getNote() = note
}