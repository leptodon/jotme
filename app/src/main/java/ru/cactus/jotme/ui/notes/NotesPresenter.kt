package ru.cactus.jotme.ui.notes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.cactus.jotme.repository.db.NotesRepository
import ru.cactus.jotme.repository.entity.Note
import kotlin.coroutines.CoroutineContext

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
     * Список тестовых данных для отображения на главном экране
     */
    override fun getNotes() {
        launch(coroutineContext){
            notesRepository.getAll().catch {}.collect {
                    value -> view.addListToView(value)
            }
        }
    }
}

