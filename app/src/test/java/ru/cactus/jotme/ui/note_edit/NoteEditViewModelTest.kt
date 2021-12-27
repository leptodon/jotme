package ru.cactus.jotme.ui.note_edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import ru.cactus.jotme.repository.db.DatabaseRepository
import ru.cactus.jotme.repository.entity.Note

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NoteEditViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var databaseRepository: DatabaseRepository
    private var viewModel: NoteEditViewModel? = null
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        databaseRepository = Mockito.mock(DatabaseRepository::class.java)
        viewModel = NoteEditViewModel(databaseRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun checkSetSaveNote() {
        viewModel?.setNoteSave()
        Assert.assertEquals(false, viewModel?.showSaveToast?.value)
    }

    @Test
    fun checkSetDeleteNote() {
        viewModel?.setNoteDelete()
        Assert.assertEquals(false, viewModel?.showDeleteToast?.value)
    }

    @Test
    fun checkSaveNewNote(): Unit = runBlocking {
        launch(Dispatchers.Main) {
            val testNote = Note(999, "Test_title", "Test_body")
            viewModel?.saveNote(testNote.id, testNote.title, testNote.body)
            verify(databaseRepository, times(1)).updateInsert(testNote)
        }
    }

}