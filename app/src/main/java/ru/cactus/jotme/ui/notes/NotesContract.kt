package ru.cactus.jotme.ui.notes

import ru.cactus.jotme.repository.entity.Note

/**
 * Интерфейсы для работы с фрагментом отображения списка заметок NotesFragment
 */
interface NotesContract {
    interface View {
        /**
         * Открытие фрагмента превью заметки
         */
        fun startPreviewFragment(note:Note)

        /**
         * Передача списка заметок в адаптер
         */
        fun addListToView(list: List<Note>)
    }

    interface Presenter {
        /**
         * Обработка нажатия на карточку заметки в RecyclerView
         * @param note объект заметки
         */
        fun onNoteClick(note: Note)

        /**
         * Получение списка заметок из репозитория
         */
        fun getNotes()
    }
}