package com.manujain.notesy.feature_notes.domain.usecase

import com.manujain.notesy.feature_notes.domain.model.Note
import com.manujain.notesy.feature_notes.domain.repository.NotesRepository

class GetNote(
    private val notesRepository: NotesRepository
) {

    suspend operator fun invoke(id: String): Note {
        return notesRepository.getNote(id)
    }
}
