package com.manujain.flashnotes.domain.utils

import com.manujain.flashnotes.domain.model.Note

object Utils {

    fun didNoteContentChange(
        note: Note,
        noteState: NoteState
    ) = (note.title == noteState.title && note.content == noteState.content)
}
