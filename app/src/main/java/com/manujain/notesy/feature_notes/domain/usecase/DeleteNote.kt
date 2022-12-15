package com.manujain.notesy.feature_notes.domain.usecase

import com.manujain.notesy.feature_notes.domain.model.Note
import com.manujain.notesy.feature_notes.domain.repository.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteNote(
    private val repository: NotesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(note: Note) {
        withContext(dispatcher) {
            repository.deleteNote(note)
        }
    }
}
