package com.manujain.flashnotes.domain.usecase

import com.manujain.flashnotes.domain.model.Note
import com.manujain.flashnotes.domain.repository.NotesRepository
import com.manujain.flashnotes.domain.utils.NotesOrder
import com.manujain.flashnotes.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val notesRepository: NotesRepository
) {

    operator fun invoke(order: NotesOrder): Flow<List<Note>> {
        return notesRepository.getNotes().map { it ->
            when (order.orderType) {
                OrderType.ASCENDING -> {
                    when (order) {
                        is NotesOrder.Title -> it.sortedBy { it.title }
                        is NotesOrder.Color -> it.sortedBy { it.color }
                        is NotesOrder.Date -> it.sortedBy { it.timestamp }
                    }
                }
                OrderType.DESCENDING -> {
                    when (order) {
                        is NotesOrder.Title -> it.sortedByDescending { it.title }
                        is NotesOrder.Color -> it.sortedByDescending { it.color }
                        is NotesOrder.Date -> it.sortedByDescending { it.timestamp }
                    }
                }
            }
        }
    }
}
