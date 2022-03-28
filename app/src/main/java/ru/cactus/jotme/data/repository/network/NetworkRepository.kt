package ru.cactus.jotme.data.repository.network

import ru.cactus.jotme.domain.entity.Note

interface NetworkRepository {
    suspend fun getNote(id: Int): Note
}