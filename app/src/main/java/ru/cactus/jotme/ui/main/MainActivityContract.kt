package ru.cactus.jotme.ui.main

interface MainActivityContract {

    interface View {
        fun startNoteActivity()
    }

    interface Presenter {
        fun addNewNote()
    }
}