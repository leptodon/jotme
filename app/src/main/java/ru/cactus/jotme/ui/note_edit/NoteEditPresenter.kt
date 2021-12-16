package ru.cactus.jotme.ui.note_edit

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
//    private var model: NoteEditContract.Model = NoteEditModel()

    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    /**
     * Сохраняем заметку в shared preferences
     * @param sharedPref передаем из активити
     * @param title текст заголовка заметки
     * @param body основной текст заметки
     */
    override fun addNewNote(title: String, body: String) {
        launch(coroutineContext) {
            notesRepository.updateInsert(
                Note(null, title, body)
            ).catch {}
                .collect {}
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
            notesRepository.delete(id).catch{}.collect {}
        }
//        model.deleteNote(sharedPref, 0)
        view.showDeleteToast()
    }

//    /**
//     * Отдаем сохраненный Note
//     */
//    override fun getNote(id: Int) {
//        launch(coroutineContext) {
//            notesRepository.getNote(id).catch { }.collect {
//                view.showNote(it)
//            }
//        }
//    }


}