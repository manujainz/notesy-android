package com.manujain.notesy.core.theme.repository

import android.content.Context
import androidx.core.graphics.toColorInt
import com.manujain.notesy.core.Utils
import com.manujain.notesy.core.theme.model.NotesyBackground
import com.manujain.notesy.core.theme.model.NotesyColor

class NotesyBackgroundProviderImpl(private val context: Context) : NotesyBackgroundProvider {

    companion object {
        val DEFAULT_COLOR = NotesyColor(NotesyBackground.DEFAULT.name, "#F7F7F7", "#1C1C1C")
    }

    private val colorMap = HashMap<NotesyBackground, NotesyColor>().apply {
        put(
            NotesyBackground.DEFAULT,
            NotesyColor(NotesyBackground.DEFAULT.name, "#F7F7F7", "#1C1C1C")
        )
        put(
            NotesyBackground.YELLOW,
            NotesyColor(NotesyBackground.YELLOW.name, "#f7d794", "#ffa801")
        )
        put(
            NotesyBackground.BLUE,
            NotesyColor(NotesyBackground.BLUE.name, "#778beb", "#0a3d62")
        )
        put(
            NotesyBackground.GREEN,
            NotesyColor(NotesyBackground.GREEN.name, "#55E6C1", "#58B19F")
        )
        put(
            NotesyBackground.ORANGE,
            NotesyColor(NotesyBackground.ORANGE.name, "#f3a683", "#cd6133")
        )
        put(
            NotesyBackground.RED,
            NotesyColor(NotesyBackground.RED.name, "#ff6b6b", "#b33939")
        )
        put(
            NotesyBackground.PURPLE,
            NotesyColor(NotesyBackground.PURPLE.name, "#a29bfe", "#474787")
        )
    }

    override fun getBackgrounds(): List<NotesyBackground> = colorMap.keys.toList()

    override fun getColors(): List<NotesyColor> = colorMap.values.toList()

    override fun getColor(background: NotesyBackground): Int {
        var notesyColor = colorMap[background]
        if (notesyColor == null) {
            notesyColor = DEFAULT_COLOR
        }
        return if (Utils.isNightMode(context)) notesyColor.dark.toColorInt() else notesyColor.light.toColorInt()
    }

    override fun getNotesyColor(background: NotesyBackground): NotesyColor {
        var notesyColor = colorMap[background]
        if (notesyColor == null) {
            notesyColor = DEFAULT_COLOR
        }
        return notesyColor
    }

    override fun getComplementColor(background: NotesyBackground): Int {
        val notesyColor = getNotesyColor(background)
        return if (Utils.isNightMode(context)) notesyColor.light.toColorInt() else notesyColor.light.toColorInt()
    }

    override fun getColor(notesyColor: NotesyColor): Int {
        return if (Utils.isNightMode(context)) notesyColor.dark.toColorInt() else notesyColor.light.toColorInt()
    }

    override fun getDefaultBackground(): NotesyBackground = NotesyBackground.DEFAULT

    override fun getDefaultNotesyColor(): NotesyColor = DEFAULT_COLOR

    override fun getDefaultColor(): Int {
        return if (Utils.isNightMode(context)) DEFAULT_COLOR.dark.toColorInt() else DEFAULT_COLOR.light.toColorInt()
    }
}
