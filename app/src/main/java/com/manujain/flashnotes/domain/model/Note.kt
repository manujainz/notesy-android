package com.manujain.flashnotes.domain.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val colors = listOf(Color.RED, Color.BLUE, Color.CYAN, Color.LTGRAY, Color.MAGENTA, Color.YELLOW, Color.WHITE)
    }
}

class InvalidNoteException(error: String): Exception()