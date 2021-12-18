package ru.cactus.jotme.ui.preview

import ru.cactus.jotme.repository.entity.Note

/**
 * Интерфейсы для работы с фрагментом отображения превью заметки
 */
interface PreviewContract {
    interface View {
        /**
         * Открытие экрана редактирования заметки
         */
        fun startEditNoteActivity()
    }

    interface Presenter {
        /**
         * Обработка нажатия на кнопку New note в MainActivity
         */
        fun onEditNoteClick()
        /**
         * Получение и хранение заметки из Bundle
         */
        fun saveIntent(note: Note)

        /**
         * Передача заметки во вью
         */
        fun getNote():Note
    }
}