package com.manujain.notesy.feature_notes.presentation.background_chooser

import androidx.lifecycle.ViewModel
import com.manujain.notesy.core.theme.model.NotesyBackground
import com.manujain.notesy.core.theme.repository.NotesyBackgroundProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class NotesyBackgroundSelectionViewModel @Inject constructor(
    private val backgroundProvider: NotesyBackgroundProvider
) : ViewModel() {

    private val _selectedBackground = MutableStateFlow(NotesyBackground.DEFAULT)
    val selectedBackground = _selectedBackground.asStateFlow()

    fun updateBackground(color: NotesyBackground) {
        _selectedBackground.value = color
    }

    fun getBackgrounds() = backgroundProvider.getBackgrounds()

    fun getSelectableBackgrounds() = getBackgrounds().map { background ->
        val isSelected = selectedBackground.value == background
        SelectableNotesyBackground(background, isSelected)
    }.toMutableList()

    fun getComplementColor(background: NotesyBackground) = backgroundProvider.getComplementColor(background)

    fun getColor(background: NotesyBackground) = backgroundProvider.getColor(background)
}
