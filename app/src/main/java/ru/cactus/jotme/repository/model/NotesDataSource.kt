package ru.cactus.jotme.repository.model

import android.content.Context
import kotlinx.coroutines.*
import ru.cactus.jotme.repository.AppDatabase
import ru.cactus.jotme.repository.DataSource
import ru.cactus.jotme.repository.db.NotesRepository
import ru.cactus.jotme.repository.entity.Note
import ru.cactus.jotme.ui.note_edit.NoteEditContract
import kotlin.coroutines.CoroutineContext

class NotesDataSource(context: Context){
    private val db: AppDatabase = AppDatabase(context = context)
    private val notesRepository: NotesRepository = NotesRepository(db)

//    fun getAllNotes(callback: NoteEditContract.Presenter) {
//        var allNotes: List<Note>
//        GlobalScope.launch(Dispatchers.IO) {
//            allNotes = notesRepository.getAll()
//
//            withContext(Dispatchers.IO) {
//                callback.onSuccess(allNotes)
//            }
//        }
//    }

    fun getNote(id: Int) {
        var note: Note
        GlobalScope.launch(Dispatchers.IO) {
            note = notesRepository.getNote(id)

            withContext(Dispatchers.IO) {
//                callback.onSuccess(note)
            }
        }
    }

    fun saveNote(note: Note) {
        GlobalScope.launch(Dispatchers.IO) {
            notesRepository.updateInsert(note)
        }
    }

    fun deleteArticle(note: Note?) {
        GlobalScope.launch(Dispatchers.IO) {
            note?.let { noteSafe ->
                notesRepository.delete(noteSafe)
            }
        }
    }

}