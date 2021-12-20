package ru.cactus.jotme.ui.swiper

import ru.cactus.jotme.repository.entity.Note

/**
 * Интерфейсы передачи данных из репозитория в PageSwiper
 */
interface PageSwiperContract {
    interface View {
        /**
         * Передача списка заметок в адаптер
         * @param list список заметок
         */
        fun addListToView(list: List<Note>)
    }

    interface Presenter {
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