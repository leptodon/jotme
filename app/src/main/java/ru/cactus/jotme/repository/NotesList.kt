package ru.cactus.jotme.repository

import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.utils.MockString

open class NotesList {
    private val noteList = mutableListOf<Note>()
    fun getNotes(): List<Note> {
        noteList.add(
            Note(
                0,
                MockString.testNoteText1.substring(0..10),
                MockString.testNoteText1
            )
        )
        noteList.add(
            Note(
                1,
                MockString.testNoteText2.substring(0..10),
                MockString.testNoteText2
            )
        )
        noteList.add(
            Note(
                2,
                MockString.testNoteText3.substring(0..10),
                MockString.testNoteText3
            )
        )
        noteList.add(
            Note(
                3,
                MockString.testNoteText4.substring(0..10),
                MockString.testNoteText4
            )
        )
        noteList.add(
            Note(
                4,
                MockString.testNoteText5.substring(0..10),
                MockString.testNoteText5
            )
        )
        noteList.add(
            Note(
                5,
                MockString.testNoteText6.substring(0..10),
                MockString.testNoteText6
            )
        )
        noteList.add(
            Note(
                6,
                MockString.testNoteText7.substring(0..10),
                MockString.testNoteText7
            )
        )
        return noteList
    }
}