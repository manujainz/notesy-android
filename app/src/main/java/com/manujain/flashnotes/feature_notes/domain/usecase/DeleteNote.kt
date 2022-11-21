package com.manujain.flashnotes.feature_notes.domain.usecase

import com.manujain.flashnotes.feature_notes.domain.model.Note
import com.manujain.flashnotes.feature_notes.domain.repository.NotesRepository

class DeleteNote(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}
