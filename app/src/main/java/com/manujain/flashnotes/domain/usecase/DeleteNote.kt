package com.manujain.flashnotes.domain.usecase

import com.manujain.flashnotes.domain.model.Note
import com.manujain.flashnotes.domain.repository.NotesRepository

class DeleteNote(
  private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}