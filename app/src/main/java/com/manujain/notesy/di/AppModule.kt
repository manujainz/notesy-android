package com.manujain.notesy.di

import androidx.room.Room
import com.manujain.notesy.MainApplication
import com.manujain.notesy.feature_notes.data.data_source.NotesDatabase
import com.manujain.notesy.feature_notes.data.repository.NotesRepositoryImpl
import com.manujain.notesy.feature_notes.domain.repository.NotesRepository
import com.manujain.notesy.feature_notes.domain.usecase.AddNote
import com.manujain.notesy.feature_notes.domain.usecase.DeleteNote
import com.manujain.notesy.feature_notes.domain.usecase.GetNote
import com.manujain.notesy.feature_notes.domain.usecase.GetNotes
import com.manujain.notesy.feature_notes.domain.usecase.NotesUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplication() = MainApplication.instance

    @Provides
    fun provideNotesDatabase(app: MainApplication): NotesDatabase {
        return Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NotesDatabase): NotesRepository {
        return NotesRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun getNotesUsecase(notesRepository: NotesRepository): NotesUsecase {
        return NotesUsecase(
            addNote = AddNote(notesRepository),
            getNote = GetNote(notesRepository),
            getNotes = GetNotes(notesRepository),
            deleteNote = DeleteNote(notesRepository)
        )
    }
}
