package ru.cactus.jotme.repository.network

class NetworkRepository(private val apiHelper: ApiHelper) {
    suspend fun getNote(id:Int) = apiHelper.getNote(id)
}