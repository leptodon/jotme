package ru.cactus.jotme.ui.note_edit

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import ru.cactus.jotme.repository.db.DatabaseRepository

@RunWith(MockitoJUnitRunner::class)
class NoteEditViewModelTest {

    @Rule
    @JvmField
    var initRule: MockitoRule = MockitoJUnit.rule()
    private val databaseRepository: DatabaseRepository = Mockito.mock(DatabaseRepository::class.java)
    private var viewModel: NoteEditViewModel? = null


    @Before
    fun setUp() {
        viewModel = NoteEditViewModel(databaseRepository)
    }

    @Test
    fun checkSetSaveNote() {
        viewModel?.setNoteSave()
        Assert.assertEquals(false, viewModel?.isNoteSave?.value)
    }

    @Test
    fun checkSetDeleteNote() {
        viewModel?.setNoteDelete()
        Assert.assertEquals(false, viewModel?.isNoteDelete?.value)
    }

    @Test
    fun checkSaveNewNote() {
        Assert.assertEquals(Unit, viewModel?.saveNote(0, "Test_title", "Test_body"))
    }

}