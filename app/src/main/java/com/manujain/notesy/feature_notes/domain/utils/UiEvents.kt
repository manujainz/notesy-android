package com.manujain.notesy.feature_notes.domain.utils

import com.manujain.notesy.core.theme.model.NotesyBackground
import com.manujain.notesy.feature_notes.domain.model.Note

sealed class NotesUiEvent {
    data class Order(val order: NotesOrder) : NotesUiEvent()
    data class DeleteNote(val note: Note) : NotesUiEvent()
    object RestoreNote : NotesUiEvent()
}

sealed class ComposeNoteUiEvent {
    object DeleteNote : ComposeNoteUiEvent()
    object AddNote : ComposeNoteUiEvent()
    class OnBackgroundChange(val color: NotesyBackground) : ComposeNoteUiEvent()
    class OnTitleChange(val title: String) : ComposeNoteUiEvent()
    class OnContentChange(val content: String) : ComposeNoteUiEvent()
}
