package com.manujain.flashnotes.domain.usecase

import com.manujain.flashnotes.domain.model.Note
import com.manujain.flashnotes.domain.repository.NotesRepository

class GetNote(
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(id: String): Note {
        return notesRepository.getNote(id)
    }
}
