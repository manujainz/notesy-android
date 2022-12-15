package com.manujain.notesy.feature_scribblepad.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manujain.notesy.feature_scribblepad.domain.model.Scribble
import com.manujain.notesy.feature_scribblepad.domain.repository.ScribblePadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ScribblePadViewModel @Inject constructor(
    private val repository: ScribblePadRepository
) : ViewModel() {

    private val _scribble = MutableStateFlow(Scribble(""))
    val scribble = _scribble.asStateFlow()

    private var scribbleJob: Job? = null

    init {
        viewModelScope.launch {
            _scribble.value = repository.getScribble()
        }
    }

    fun onScribbleUpdate(data: String) {
        _scribble.value = Scribble(data)
        // cancel existing job if running
        clearJob()
        scribbleJob = viewModelScope.launch {
            repository.storeScribble(scribble.value)
        }
    }

    override fun onCleared() {
        clearJob()
        super.onCleared()
    }

    private fun clearJob() {
        scribbleJob?.cancel()
        scribbleJob = null
    }
}
