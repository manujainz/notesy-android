package com.manujain.flashnotes.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manujain.flashnotes.domain.model.Note
import com.manujain.flashnotes.domain.usecase.NotesUsecase
import com.manujain.flashnotes.domain.utils.NotesOrder
import com.manujain.flashnotes.domain.utils.NotesUiEvent
import com.manujain.flashnotes.domain.utils.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUsecase: NotesUsecase
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private var notesJob: Job? = null

    init {
        getNotes(NotesOrder.Date(OrderType.DESCENDING))
    }

    suspend fun onEvent(notesUiEvent: NotesUiEvent) {
        when (notesUiEvent) {
            is NotesUiEvent.Order -> getNotes(notesUiEvent.order)
            is NotesUiEvent.DeleteNote -> viewModelScope.launch { deleteNote(notesUiEvent.note) }
        }
    }

    private suspend fun deleteNote(note: Note) {
        notesUsecase.deleteNote(note)
    }

    private fun getNotes(notesOrder: NotesOrder) {
        // check if the job handle needs to be cancelled after every invocation
        notesJob?.cancel()
        notesJob = notesUsecase.getNotes(notesOrder).onEach { notes ->
            _notes.value = notes
        }.launchIn(viewModelScope)
    }
}
