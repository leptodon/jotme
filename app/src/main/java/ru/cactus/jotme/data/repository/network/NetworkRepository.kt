package ru.cactus.jotme.data.repository.network

import ru.cactus.jotme.data.repository.network.entity.NetworkNote
import ru.cactus.jotme.domain.entity.Note

class NetworkRepository(private val apiService: ApiService) {
    suspend fun getNote(id: Int): Note = toDomainModelConverter(apiService.getNote(id))

    private fun toDomainModelConverter(networkNote: NetworkNote) =
        Note(
            networkNote.id,
            networkNote.title,
            networkNote.body
        )
}