package ru.cactus.jotme.ui.main

class MainPresenter(
    private val view: MainActivityContract.View
) : MainActivityContract.Presenter {

    /**
     * Открытие экрана редактирования заметки при нажатии "New Note"
     */
    override fun addNewNote() {
        view.startNoteActivity()
    }

}