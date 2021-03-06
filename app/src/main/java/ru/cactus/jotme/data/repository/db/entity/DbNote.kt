package ru.cactus.jotme.data.repository.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


/**
 * Сущность заметки
 * @param id идентификатор в БД
 * @param title заголовок заметки
 * @param body тело заметки
 */
@Entity(tableName = "notes")
@Parcelize
data class DbNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val body: String
) : Parcelable {
    override fun toString(): String {
        val fullText = StringBuilder()
        fullText.append(title)
        fullText.append("\n")
        fullText.append(body)
        return fullText.toString()
    }
}