package com.manujain.flashnotes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manujain.flashnotes.domain.model.InvalidNoteException
import com.manujain.flashnotes.domain.model.Note
import com.manujain.flashnotes.domain.usecase.NotesUsecase
import com.manujain.flashnotes.domain.utils.AddEditNoteUiEvent
import com.manujain.flashnotes.domain.utils.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCase: NotesUsecase
): ViewModel() {

    private val _noteState = MutableStateFlow(NoteState())
    val noteState = _noteState.asStateFlow()

    var currentNote: Note? = null

    fun onEvent(event: AddEditNoteUiEvent) {
        when(event) {
            is AddEditNoteUiEvent.DeleteNote -> {}
            is AddEditNoteUiEvent.OnColorChange -> _noteState.value = noteState.value.copy(color = event.color)
            is AddEditNoteUiEvent.OnTitleChange -> _noteState.value = noteState.value.copy(title = event.title)
            is AddEditNoteUiEvent.OnContentChange -> _noteState.value = noteState.value.copy(content = event.content)
            is AddEditNoteUiEvent.AddNote -> addNote()
        }
    }

    private fun addNote() {
        viewModelScope.launch {
            try {
                notesUseCase.addNote(
                    Note(
                        title = noteState.value.title,
                        content = noteState.value.content,
                        color = noteState.value.color,
                        timestamp = System.currentTimeMillis(),
                        id = currentNote?.id ?: UUID.randomUUID().toString()
                    )
                )
            } catch (e: InvalidNoteException) {
                Timber.e(e, e.message)
            }
        }
    }

}