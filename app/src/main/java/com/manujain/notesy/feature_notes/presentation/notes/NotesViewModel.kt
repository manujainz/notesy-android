package com.manujain.notesy.feature_notes.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manujain.notesy.core.isDiff
import com.manujain.notesy.feature_notes.domain.model.Note
import com.manujain.notesy.feature_notes.domain.usecase.NotesUsecase
import com.manujain.notesy.feature_notes.domain.utils.NotesOrder
import com.manujain.notesy.feature_notes.domain.utils.NotesUiEvent
import com.manujain.notesy.feature_notes.domain.utils.OrderType
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

    private var fetchNotesJob: Job? = null
    private var currentOrder: NotesOrder = NotesOrder.Date(OrderType.DESCENDING)
    private var recentlyDeletedNote: Note? = null

    init {
        getNotes(currentOrder)
    }

    fun onEvent(event: NotesUiEvent) {
        when (event) {
            is NotesUiEvent.Order -> {
                if (event.order.isDiff(currentOrder)) {
                    event.order.also { order ->
                        currentOrder = order
                        getNotes(order)
                    }
                }
            }
            is NotesUiEvent.DeleteNote -> {
                viewModelScope.launch {
                    deleteNote(event.note)
                }
            }
            is NotesUiEvent.RestoreNote -> {
                recentlyDeletedNote?.let { note ->
                    viewModelScope.launch {
                        notesUsecase.addNote(note)
                        recentlyDeletedNote = null
                    }
                }
            }
        }
    }

    private suspend fun deleteNote(note: Note) {
        recentlyDeletedNote = note
        notesUsecase.deleteNote(note)
    }

    private fun getNotes(notesOrder: NotesOrder) {
        // NOTE: Check if the job handle needs to be cancelled after every invocation
        fetchNotesJob?.cancel()
        fetchNotesJob = notesUsecase.getNotes(notesOrder).onEach { notes ->
            _notes.value = notes
        }.launchIn(viewModelScope)
    }
}
