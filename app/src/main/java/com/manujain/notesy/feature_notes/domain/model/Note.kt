package com.manujain.notesy.feature_notes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manujain.notesy.core.theme.model.NotesyBackground

@Entity
data class Note(
    val title: String,
    val content: String,
    val color: NotesyBackground,
    val created: Long,
    val lastModified: Long,
    @PrimaryKey
    val id: String
) {
    companion object {
        const val NOTE_ID = "noteId"
    }
}

class InvalidNoteException(error: String) : Exception(error)
