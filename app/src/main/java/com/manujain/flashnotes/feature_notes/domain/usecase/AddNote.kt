package com.manujain.flashnotes.feature_notes.domain.usecase

import com.manujain.flashnotes.feature_notes.domain.model.InvalidNoteException
import com.manujain.flashnotes.feature_notes.domain.model.Note
import com.manujain.flashnotes.feature_notes.domain.repository.NotesRepository

class AddNote(
    private val notesRepository: NotesRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Invalid Note. Title is empty")
        }

        if (note.content.isBlank()) {
            throw InvalidNoteException("Invalid Note. Content is empty.")
        }

        notesRepository.insertNote(note)
    }
}
