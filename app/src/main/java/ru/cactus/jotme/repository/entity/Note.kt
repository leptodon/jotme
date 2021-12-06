package ru.cactus.jotme.repository.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    val id: Int,
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