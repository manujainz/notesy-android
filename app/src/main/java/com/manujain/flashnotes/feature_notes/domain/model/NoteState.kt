package com.manujain.flashnotes.feature_notes.domain.model

data class NoteState(
    val title: String = "",
    val content: String = "",
    val color: Int = Note.colors.random(),
    val isLoading: Boolean = false
)
