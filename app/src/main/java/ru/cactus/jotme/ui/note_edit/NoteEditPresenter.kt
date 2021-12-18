package ru.cactus.jotme.ui.note_edit

import kotlinx.coroutines.*
import ru.cactus.jotme.repository.db.NotesRepository
import ru.cactus.jotme.repository.entity.Note
import kotlin.coroutines.CoroutineContext

/**
 * Класс обрабатывающий действия пользователя во вью
 * и передача данных из репозитория во вью
 * @param view NoteEditActivity экран редактирования заметки
 * @param notesRepository репозиторий БД
 */
class NoteEditPresenter(
    private val view: NoteEditContract.View,
    private val notesRepository: NotesRepository
) : NoteEditContract.Presenter, CoroutineScope {

    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    /**
     * Сохраняем заметку в shared preferences
     * @param title текст заголовка заметки
     * @param body основной текст заметки
     */
    override fun onClickNewNoteBtn(id: Int?, title: String, body: String) {
        when {
            title.isNotEmpty() && body.isNotEmpty() -> {
                launch(coroutineContext) {
                    notesRepository.updateInsert(
                        Note(id, title, body)
                    )
                }
            }
        }
    }

    /**
     * Вызываем intent для передачи заметки в другое приложение
     */
    override fun onClickShareBtn(note: Note) {
        view.shareNote(note)
    }

    /**
     * Удаление заметки
     */
    override fun deleteNote(id: Int) {
        launch(coroutineContext) {
            notesRepository.delete(id)
        }
        view.showDeleteToast()
    }

    /**
     * Удаление coroutineContext при уничтожении presenter
     */
    override fun onDestroy() {
        CoroutineScope(coroutineContext).cancel()
    }
}