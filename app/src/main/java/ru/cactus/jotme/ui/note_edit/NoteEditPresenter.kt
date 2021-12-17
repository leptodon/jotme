package ru.cactus.jotme.ui.note_edit

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import ru.cactus.jotme.repository.db.NotesRepository
import ru.cactus.jotme.repository.entity.Note
import kotlin.coroutines.CoroutineContext

class NoteEditPresenter(
    private val view: NoteEditContract.View,
    private val notesRepository: NotesRepository
) : NoteEditContract.Presenter, CoroutineScope {

    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    /**
     * Сохраняем заметку в shared preferences
     * @param sharedPref передаем из активити
     * @param title текст заголовка заметки
     * @param body основной текст заметки
     */
    override fun addNewNote(id:Int?, title: String, body: String) {
        launch(coroutineContext) {
            notesRepository.updateInsert(
                Note(id, title, body)
            )
        }
    }

    /**
     * Вызываем intent для передачи заметки в другое приложение
     */
    override fun shareNote(note: Note) {
        view.shareNote(note)
    }

    /**
     * Удаление заметки
     */
    override fun deleteNote(id:Int) {
        launch(coroutineContext) {
            notesRepository.delete(id).catch{
                    e -> Log.d("DB", e.message.toString())
            }.collect {}
        }
        view.showDeleteToast()
    }
}