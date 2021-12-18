package ru.cactus.jotme.ui.main

/**
 * Интерфейсы для работы с MainActivity и MainActivityPresenter
 */
interface MainActivityContract {

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
        fun onClickNewNoteBtn()
    }
}