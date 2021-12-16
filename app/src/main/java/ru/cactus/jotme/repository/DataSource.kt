package ru.cactus.jotme.repository

import ru.cactus.jotme.repository.entity.Note

interface DataSource {

    interface Presenter {
        fun onSuccess(notesList: List<Note>)
        fun onSuccess(notes: Note)
    }
}