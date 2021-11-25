package ru.cactus.jotme.repository.entity

data class Note(
    val id: Int,
    val title: String,
    val body: String
){
    override fun toString(): String {
        val sbText = StringBuilder()
        sbText.append(title)
        sbText.append("\n")
        sbText.append(body)
        return sbText.toString()
    }
}