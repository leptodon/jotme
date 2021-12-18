package ru.cactus.jotme.ui.swiper

import android.util.Log
import kotlinx.coroutines.*
import ru.cactus.jotme.repository.db.NotesRepository
import kotlin.coroutines.CoroutineContext

class PageSwiperPresenter(
    private val view: PageSwiperContract.View,
    private val notesRepository: NotesRepository
) : PageSwiperContract.Presenter, CoroutineScope {

    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main


    override fun getNotesList() {
        launch(coroutineContext) {
            view.addListToView(notesRepository.getAll())
            Log.d("FROMDB_IN_PRESENTER", notesRepository.getAll().toString())
        }
    }

    /**
     * Удаление coroutineContext при уничтожении presenter
     */
    override fun onDestroy() {
        CoroutineScope(coroutineContext).cancel()
    }
}