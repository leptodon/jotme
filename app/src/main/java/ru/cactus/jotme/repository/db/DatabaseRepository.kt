package ru.cactus.jotme.repository.db

import ru.cactus.jotme.repository.entity.Note

interface DatabaseRepository {
    suspend fun updateInsert(note: Note)
    suspend fun getAll(): List<Note>
    suspend fun delete(id: Int)
}