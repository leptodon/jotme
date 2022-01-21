package ru.cactus.jotme.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
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
