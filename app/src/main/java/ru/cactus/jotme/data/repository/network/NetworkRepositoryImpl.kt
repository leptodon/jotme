package ru.cactus.jotme.data.repository.network

import ru.cactus.jotme.data.fromNetworkModelConverter
import ru.cactus.jotme.domain.entity.Note

class NetworkRepositoryImpl(private val apiService: ApiService) : NetworkRepository {
    override suspend fun getNote(id: Int): Note = fromNetworkModelConverter(apiService.getNote(id))
}