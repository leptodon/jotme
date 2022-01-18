package ru.cactus.jotme.data.repository.db

import ru.cactus.jotme.data.repository.db.entity.DbNote

interface DatabaseRepository {
    suspend fun updateInsert(dbNote: DbNote)
    suspend fun getAll(): List<DbNote>
    suspend fun delete(id: Int)
}