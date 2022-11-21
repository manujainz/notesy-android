package com.manujain.flashnotes.feature_scribblepad.domain.repository

import com.manujain.flashnotes.feature_scribblepad.domain.model.Scribble

interface ScribblePadRepository {

    suspend fun getScribble(): Scribble

    suspend fun storeScribble(scribble: Scribble)

    suspend fun deleteScribble()
}
