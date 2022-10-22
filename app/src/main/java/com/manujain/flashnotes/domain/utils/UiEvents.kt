package com.manujain.flashnotes.domain.utils

import com.manujain.flashnotes.domain.model.Note

sealed class NotesUiEvent {
    data class Order(val order: NotesOrder) : NotesUiEvent()
    data class DeleteNote(val note: Note) : NotesUiEvent()
}

sealed class AddEditNoteUiEvent {
    object DeleteNote : AddEditNoteUiEvent()
    object AddNote : AddEditNoteUiEvent()
    class OnColorChange(val color: Int) : AddEditNoteUiEvent()
    class OnTitleChange(val title: String) : AddEditNoteUiEvent()
    class OnContentChange(val content: String) : AddEditNoteUiEvent()
}
