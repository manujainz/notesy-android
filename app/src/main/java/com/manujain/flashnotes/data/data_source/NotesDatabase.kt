package com.manujain.flashnotes.data.data_source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.manujain.flashnotes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DB_NAME = "notes_db"
    }
}
