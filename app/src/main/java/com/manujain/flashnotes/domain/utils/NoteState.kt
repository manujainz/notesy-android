package com.manujain.flashnotes.domain.utils

import com.manujain.flashnotes.domain.model.Note

data class NoteState(
    val title: String = "",
    val content: String = "",
    val color: Int = Note.colors.random(),
    val isLoading: Boolean = false
)
