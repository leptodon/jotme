package ru.cactus.jotme.ui.notes

import ru.cactus.jotme.repository.entity.Note

interface NotesContract {
    interface View {
        fun startPreviewFragment(note:Note)
        fun addListToView(list: List<Note>)
    }

    interface Presenter {
        fun onNoteClick(note: Note)
        fun getNotes()
    }
}