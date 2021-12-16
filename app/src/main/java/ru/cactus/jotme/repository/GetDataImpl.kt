package ru.cactus.jotme.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.cactus.jotme.repository.db.NotesRepository

class GetDataImpl(private val notesRepository: NotesRepository):GetData {

    override suspend fun collect(listener: GetData.CompleteListener) {
        withContext(Dispatchers.IO){
            launch {
                listener.onComplete(notesRepository.getAll())
            }
        }
    }
}