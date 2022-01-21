package ru.cactus.jotme.data.repository.network

import ru.cactus.jotme.data.fromNetworkModelConverter
import ru.cactus.jotme.domain.entity.Note

class NetworkRepository(private val apiService: ApiService) {
    suspend fun getNote(id: Int): Note = fromNetworkModelConverter(apiService.getNote(id))
}