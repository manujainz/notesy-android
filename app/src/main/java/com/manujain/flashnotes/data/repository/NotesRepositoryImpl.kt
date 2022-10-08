package com.manujain.flashnotes.data.repository

import com.manujain.flashnotes.data.data_source.NoteDao
import com.manujain.flashnotes.domain.model.Note
import com.manujain.flashnotes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(private val dao: NoteDao): NotesRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNote(noteId: Int): Note {
        return dao.getNote(noteId)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.insertNote(note)
    }
}