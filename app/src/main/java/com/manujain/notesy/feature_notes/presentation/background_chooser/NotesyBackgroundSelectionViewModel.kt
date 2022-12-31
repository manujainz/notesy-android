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

    private val _selectedColor = MutableStateFlow(NotesyBackground.DEFAULT)
    val selectedColor = _selectedColor.asStateFlow()

    fun updateColor(color: NotesyBackground) {
        _selectedColor.value = color
    }

    fun getBackgrounds() = backgroundProvider.getBackgrounds()

    fun getComplementColor(background: NotesyBackground) = backgroundProvider.getComplementColor(background)

    fun getColor(background: NotesyBackground) = backgroundProvider.getColor(background)
}
