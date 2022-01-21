package ru.cactus.jotme.data.repository.network.entity

import com.google.gson.annotations.SerializedName


/**
 * Сущность заметки из Network
 * @param id идентификатор
 * @param title заголовок заметки
 * @param body тело заметки
 */
data class NetworkNote(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
)