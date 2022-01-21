package ru.cactus.jotme.data

import ru.cactus.jotme.data.repository.db.entity.DbNote
import ru.cactus.jotme.data.repository.network.entity.NetworkNote
import ru.cactus.jotme.domain.entity.Note

fun fromDbModelConverter(dbNote: DbNote) =
    Note(
        dbNote.id,
        dbNote.title,
        dbNote.body
    )

fun toDatabaseModelConverter(note: Note) =
    DbNote(
        note.id,
        note.title,
        note.body
    )

fun fromNetworkModelConverter(networkNote: NetworkNote) =
    Note(
        networkNote.id,
        networkNote.title,
        networkNote.body
    )