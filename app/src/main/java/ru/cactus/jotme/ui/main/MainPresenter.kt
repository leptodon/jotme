package ru.cactus.jotme.ui.main

class MainPresenter(
    private val view: MainActivityContract.View
) : MainActivityContract.Presenter {

    override fun addNewNote() {
        view.startNoteActivity()
    }

}
