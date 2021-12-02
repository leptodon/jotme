package ru.cactus.jotme.ui.main

import ru.cactus.jotme.mock.MockString
import ru.cactus.jotme.repository.entity.Note

class NotesPresenter(private val view: NotesFragment) : NotesContract.Presenter {
    private val noteList = mutableListOf<Note>()

    override fun onNoteClick(note: Note) {
        view.startPreviewDialog(note)

    }

    override fun onEditBtnNoteClick(note: Note) {
        view.startEditNoteActivity(note)
    }

    override fun getNotes(): List<Note>{
        noteList.add(
            Note(
                0,
                MockString.testNoteText1.substring(0..10),
                MockString.testNoteText1
            )
        )
        noteList.add(
            Note(
                0,
                MockString.testNoteText2.substring(0..10),
                MockString.testNoteText2
            )
        )
        noteList.add(
            Note(
                0,
                MockString.testNoteText3.substring(0..10),
                MockString.testNoteText3
            )
        )
        noteList.add(
            Note(
                0,
                MockString.testNoteText4.substring(0..10),
                MockString.testNoteText4
            )
        )
        noteList.add(
            Note(
                0,
                MockString.testNoteText5.substring(0..10),
                MockString.testNoteText5
            )
        )
        noteList.add(
            Note(
                0,
                MockString.testNoteText6.substring(0..10),
                MockString.testNoteText6
            )
        )
        noteList.add(
            Note(
                0,
                MockString.testNoteText7.substring(0..10),
                MockString.testNoteText7
            )
        )
        return noteList
    }
}