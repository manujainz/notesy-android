package com.manujain.flashnotes.feature_notes.domain.model

import android.graphics.Color
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
            Color.RED,
            Color.BLUE,
            Color.CYAN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.YELLOW,
            Color.WHITE
        )

        const val NOTE_ID = "noteId"
    }
}

class InvalidNoteException(error: String) : Exception(error)
