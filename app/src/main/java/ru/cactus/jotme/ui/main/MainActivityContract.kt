package ru.cactus.jotme.ui.main

import ru.cactus.jotme.repository.entity.Note

interface MainActivityContract {

    interface View {
        fun startEditNoteActivity()
    }

    interface Presenter {
        fun addNewNoteBtn()
    }
}