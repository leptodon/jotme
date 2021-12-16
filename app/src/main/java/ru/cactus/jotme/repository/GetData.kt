package ru.cactus.jotme.repository

import ru.cactus.jotme.repository.entity.Note

interface GetData {
    interface CompleteListener {
        fun onComplete(notes: List<Note>)
    }

    suspend fun collect(listener: CompleteListener)
}