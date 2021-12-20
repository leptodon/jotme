package ru.cactus.jotme.ui.note_edit

import android.content.Context
import ru.cactus.jotme.repository.entity.Note

/**
 * Интерфейсы для работы с NoteEditActivity и NoteEditPresenter
 */
interface NoteEditContract {
    interface View {

        /**
         * Отображение "тоста" при сохранении заметки
         */
        fun showSaveToast()

        /**
         * Отображение "тоста" при удалении заметки
         */
        fun showDeleteToast()

        /**
         * Передача заметки во внешние приложения
         * @param note объект заметки
         */
        fun shareNote(note: Note?)

        /**
         * Сохранение текущей заметки
         */
        fun onClickSaveBtn()

        /**
         * Возврашаем Context по требованию
         */
        fun getContext(): Context
    }

    interface Presenter {

        /**
         * Обработка нажатия на кнопку "New note"
         * @param id идентификатор в БД
         * @param title заголовок заметки
         * @param body тело заметки
         */
        fun saveNote(id: Int?, title: String, body: String)

        /**
         * Обработка нажатия на кнопку "Share"
         * @param note объект заметки
         */
        fun onClickShareBtn(note: Note)

        /**
         * Обработка нажатия на кнопку "Delete"
         * @param id идентификатор удаляемой заметки
         */
        fun deleteNote(id: Int)

        /**
         * Обработка вызова функции onDestroy во View
         */
        fun onDestroy()
    }
}