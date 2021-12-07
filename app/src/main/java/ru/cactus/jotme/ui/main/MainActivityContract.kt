package ru.cactus.jotme.ui.main

interface MainActivityContract {

    interface View {
        fun startEditNoteActivity()
    }

    interface Presenter {
        fun addNewNoteBtn()
    }
}