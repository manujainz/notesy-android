package com.manujain.notesy.core.theme.repository

import androidx.annotation.ColorInt
import com.manujain.notesy.core.theme.model.NotesyBackground
import com.manujain.notesy.core.theme.model.NotesyColor

interface NotesyBackgroundProvider {
    fun getBackgrounds(): List<NotesyBackground>

    fun getColors(): List<NotesyColor>

    @ColorInt
    fun getColor(background: NotesyBackground): Int

    @ColorInt
    fun getComplementColor(background: NotesyBackground): Int

    @ColorInt
    fun getColor(notesyColor: NotesyColor): Int

    fun getNotesyColor(background: NotesyBackground): NotesyColor

    fun getDefaultBackground(): NotesyBackground

    fun getDefaultNotesyColor(): NotesyColor

    @ColorInt
    fun getDefaultColor(): Int
}
