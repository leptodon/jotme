package ru.cactus.jotme.data.repository.network

import ru.cactus.jotme.data.repository.network.entity.NetworkNote
import ru.cactus.jotme.domain.entity.Note

class NetworkRepository(private val apiService: ApiService) {
    suspend fun getNote(id: Int): NetworkNote = apiService.getNote(id)
}