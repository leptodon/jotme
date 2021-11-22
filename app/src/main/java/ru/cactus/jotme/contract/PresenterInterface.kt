package ru.cactus.jotme.contract

import ru.cactus.jotme.Note

interface PresenterInterface {
    fun addNewNote(title: String, body: String)
    fun getAllNotes(): List<Note>
}