package ru.cactus.jotme.data.repository.network

class ApiHelper(private val apiService: ApiService) {
    suspend fun getNote(id: Int) = apiService.getNote(id)
}