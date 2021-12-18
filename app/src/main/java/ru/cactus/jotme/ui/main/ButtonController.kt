package ru.cactus.jotme.ui.main

/**
 * Интерфейс скрытия кнопки New note при переходе с главного фрагмента
 */
interface ButtonController {
    fun hideNewNoteBtn(isVisible: Boolean)
}