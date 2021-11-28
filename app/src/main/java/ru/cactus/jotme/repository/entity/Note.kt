package ru.cactus.jotme.repository.entity

data class Note(
    val id: Int,
    val title: String,
    val body: String
){
    override fun toString(): String {
        val fullText = StringBuilder()
        fullText.append(title)
        fullText.append("\n")
        fullText.append(body)
        return fullText.toString()
    }
}