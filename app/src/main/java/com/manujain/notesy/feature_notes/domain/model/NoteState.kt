package com.manujain.notesy.feature_notes.domain.model

import android.graphics.Color

data class NoteState(
    val title: String = "",
    val content: String = "",
    val color: Int = Color.parseColor(Note.colors.random()),
    val isLoading: Boolean = false
)
