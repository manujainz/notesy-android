package com.manujain.notesy.feature_notes.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manujain.notesy.feature_notes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 3
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DB_NAME = "notes_db"
    }
}
