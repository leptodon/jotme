package ru.cactus.jotme.ui.swiper

import ru.cactus.jotme.repository.entity.Note

interface PageSwiperContract {
    interface View{
        /**
         * Передача списка заметок в адаптер
         */
        fun addListToView(list: List<Note>)
    }

    interface Presenter{
        /**
         * Получение списка заметок из репозитория
         */
        fun getNotesList()

        /**
         * Обработка вызова функции onDestroy во View
         */
        fun onDestroy()
    }

}