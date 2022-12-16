package com.manujain.notesy.feature_notes.domain.utils

import com.manujain.notesy.feature_notes.domain.model.Note

sealed class NotesUiEvent {
    data class Order(val order: NotesOrder) : NotesUiEvent()
    data class DeleteNote(val note: Note) : NotesUiEvent()
    object RestoreNote : NotesUiEvent()
}

sealed class AddEditNoteUiEvent {
    object DeleteNote : AddEditNoteUiEvent()
    object AddNote : AddEditNoteUiEvent()
    class OnColorChange(val color: Int) : AddEditNoteUiEvent()
    class OnTitleChange(val title: String) : AddEditNoteUiEvent()
    class OnContentChange(val content: String) : AddEditNoteUiEvent()
}