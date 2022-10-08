package com.manujain.flashnotes.di

import androidx.room.Room
import com.manujain.flashnotes.MainApplication
import com.manujain.flashnotes.data.data_source.NotesDatabase
import com.manujain.flashnotes.data.repository.NotesRepositoryImpl
import com.manujain.flashnotes.domain.repository.NotesRepository
import com.manujain.flashnotes.domain.usecase.AddNote
import com.manujain.flashnotes.domain.usecase.DeleteNote
import com.manujain.flashnotes.domain.usecase.GetNote
import com.manujain.flashnotes.domain.usecase.GetNotes
import com.manujain.flashnotes.domain.usecase.NotesUsecase
import dagger.Provides

class AppModule {

    @Provides
    fun provideApplication() = MainApplication.instance

    @Provides
    fun provideNotesDatabase(app: MainApplication): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DB_NAME
        ).build()
    }

    @Provides
    fun provideNoteRepository(db: NotesDatabase): NotesRepository {
        return NotesRepositoryImpl(db.noteDao)
    }

    @Provides
    fun getNotesUsecase(notesRepository: NotesRepository): NotesUsecase {
        return NotesUsecase(
            addNote = AddNote(notesRepository),
            getNote = GetNote(notesRepository),
            getNotes = GetNotes(notesRepository),
            deleteNote = DeleteNote(notesRepository)
        )
    }
}