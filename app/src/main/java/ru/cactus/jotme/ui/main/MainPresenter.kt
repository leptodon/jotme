package ru.cactus.jotme.ui.main

/**
 * Класс обрабатывающий действия пользователя во вью
 * @param view MainActivity Главный экран
 */
class MainPresenter(
    private val view: MainActivityContract.View
) : MainActivityContract.Presenter {

    /**
     * Открытие экрана редактирования заметки при нажатии "New Note"
     */
    override fun onClickNewNoteBtn() {
        view.startEditNoteActivity()
    }
}