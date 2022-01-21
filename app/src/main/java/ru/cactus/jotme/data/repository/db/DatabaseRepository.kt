package ru.cactus.jotme.data.repository.db

import ru.cactus.jotme.domain.entity.Note

interface DatabaseRepository {
    suspend fun updateInsert(note: Note)
    suspend fun getAll(): List<Note>
    suspend fun delete(id: Int)
}