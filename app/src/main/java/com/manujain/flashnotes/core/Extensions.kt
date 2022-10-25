package com.manujain.flashnotes.core

import com.manujain.flashnotes.domain.model.Note
import com.manujain.flashnotes.domain.utils.NoteState

fun Note?.isDiff(state: NoteState): Boolean {
    return (
        (
            (this == null) ||
                (this.title != state.title) ||
                (this.content != state.content) ||
                (this.color != state.color)
            )
        )
}
