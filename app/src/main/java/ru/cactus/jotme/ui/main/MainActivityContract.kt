package ru.cactus.jotme.ui.main

import android.content.SharedPreferences

interface MainActivityContract {

    interface View {
        fun startNoteActivity()
    }

    interface Presenter {
        fun addNewNoteBtn()
    }
}