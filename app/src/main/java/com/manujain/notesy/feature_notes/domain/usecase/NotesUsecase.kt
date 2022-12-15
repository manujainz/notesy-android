package com.manujain.notesy.feature_notes.domain.usecase

data class NotesUsecase(
    val addNote: AddNote,
    val getNote: GetNote,
    val getNotes: GetNotes,
    val deleteNote: DeleteNote
)
