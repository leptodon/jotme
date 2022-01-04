package ru.cactus.jotme.ui.note_edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.cactus.jotme.repository.db.DatabaseRepositoryImpl
import ru.cactus.jotme.repository.entity.Note

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NoteEditViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var databaseRepositoryImpl: DatabaseRepositoryImpl
    private var viewModel: NoteEditViewModel? = null
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        databaseRepositoryImpl = mock(DatabaseRepositoryImpl::class.java)
        viewModel = NoteEditViewModel(databaseRepositoryImpl)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun checkSaveNewNote(): Unit = runBlocking {
        runTest(StandardTestDispatcher()) {
            val testNote = Note(999, "Test_title", "Test_body")
            viewModel?.saveNote(testNote.id, testNote.title, testNote.body)
            verify(databaseRepositoryImpl, times(1)).updateInsert(testNote)
            Assert.assertEquals(Unit, viewModel?.showSaveToast?.value)
        }
    }

    @Test
    fun checkDeleteNote(): Unit = runBlocking {
        runTest(StandardTestDispatcher()) {
            viewModel?.deleteNote(1)
            verify(databaseRepositoryImpl, atLeastOnce()).delete(1)
            Assert.assertEquals(Unit, viewModel?.showDeleteToast?.value)
        }
    }

}