package ru.cactus.jotme.ui.main

import ru.cactus.jotme.repository.entity.Note

interface NotesContract {
    interface View {
        fun startEditNoteActivity(note:Note)
        fun startPreviewDialog(note:Note)
    }

    interface Presenter {
        fun onNoteClick(note: Note)
        fun onEditBtnNoteClick(note:Note)
        fun getNotes():List<Note>
    }
}