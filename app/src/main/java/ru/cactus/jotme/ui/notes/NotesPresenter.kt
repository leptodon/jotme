package ru.cactus.jotme.ui.notes

import kotlinx.coroutines.*
import ru.cactus.jotme.repository.db.NotesRepository
import ru.cactus.jotme.repository.entity.Note
import kotlin.coroutines.CoroutineContext


/**
 * Класс обрабатывающий действия пользователя во вью
 * и передача данных из репозитория во вью
 * @param view NotesFragment экран RecyclerView список заметок
 * @param notesRepository репозиторий БД
 */
class NotesPresenter(
    private val view: NotesContract.View,
    private val notesRepository: NotesRepository
) : NotesContract.Presenter,
    CoroutineScope {
    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    /**
     * Открытие экрана просмотра заметки
     * @param note
     */
    override fun onNoteClick(note: Note) {
        view.startPreviewFragment(note)
    }

    /**
     * Передача данных из репозитория в RecyclerView
     */
    override fun getNotes() {
        launch(coroutineContext) {
            view.addListToView(notesRepository.getAll())
        }
    }

    /**
     * Удаление coroutineContext при уничтожении presenter
     * CoroutineScope(coroutineContext).cancel()
     */
    override fun onDestroy() {
        cancel()
    }

}