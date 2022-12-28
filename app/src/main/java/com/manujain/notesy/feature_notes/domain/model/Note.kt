package com.manujain.notesy.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val color: Int,
    val created: Long,
    val lastModified: Long,
    @PrimaryKey
    val id: String
) {
    companion object {
        val colors = listOf(
            "#F7D44C",
            "#EB7A53",
            "#98B7DB",
            "#A8D672",
            "#F6ECC9",
            "#000000",
        )

        const val NOTE_ID = "noteId"
    }
}

class InvalidNoteException(error: String) : Exception(error)
