package ru.cactus.jotme.ui.preview

import com.google.gson.Gson
import ru.cactus.jotme.repository.entity.Note

class PreviewPresenter(private val view: PreviewContract.View) : PreviewContract.Presenter {
    private lateinit var note:Note

    /**
     * Открытие экрана редактирования заметки
     */
    override fun onEditNoteClick() {
        view.startEditNoteActivity()
    }
    /**
     * Сохранение объекта Note из intent в MainActivity
     * @param json объект Note в JSON формате
     */
    override fun saveIntent(json: String) {
        note = Gson().fromJson(json, Note::class.java)
    }

    /**
     * Отдаем сохраненный Note
     */
    override fun getNote() = note
}