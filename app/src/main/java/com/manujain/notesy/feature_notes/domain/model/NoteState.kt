package com.manujain.notesy.feature_notes.domain.model

import com.manujain.notesy.core.theme.model.NotesyBackground

data class NoteState(
    val title: String = "",
    val content: String = "",
    val color: NotesyBackground = NotesyBackground.DEFAULT,
    val isLoading: Boolean = false
)
