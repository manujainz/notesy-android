package com.manujain.flashnotes.feature_notes.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manujain.flashnotes.feature_notes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DB_NAME = "notes_db"
    }
}
