package ru.cactus.jotme.contract

import ru.cactus.jotme.Note

interface ContractInterface {

    interface View {
        fun initView()
        fun updateViewData()
        fun showSaveToast()
    }

    interface Presenter {
        fun addNewNote(title: String, body: String)
        fun getAllNotes(): List<Note>
    }

    interface Model {
        fun getNote(id: Int): Note
        fun getAllNote(): List<Note>
        fun saveNote(note: Note)
        fun updateNote(note: Note): Note
        fun deleteNote(id: Int)
    }
}