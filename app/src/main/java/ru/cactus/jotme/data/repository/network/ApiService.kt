package ru.cactus.jotme.data.repository.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.cactus.jotme.data.repository.network.entity.NetworkNote

interface ApiService {
    @GET("notes/{id}/")
    suspend fun getNote(@Path("id") id: Int): NetworkNote
}