package ru.cactus.jotme.ui.preview

import ru.cactus.jotme.repository.entity.Note

interface PreviewContract {
    interface View {
        fun startEditNoteActivity()
    }

    interface Presenter {
        fun onEditNoteClick()
        fun saveIntent(note: Note)
        fun getNote():Note
    }
}