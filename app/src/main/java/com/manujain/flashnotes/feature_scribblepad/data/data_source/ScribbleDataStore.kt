package com.manujain.flashnotes.feature_scribblepad.data.data_source

import com.manujain.flashnotes.feature_scribblepad.domain.model.Scribble

interface ScribbleDataStore {

    suspend fun getScribble(): Scribble

    suspend fun storeScribble(scribble: Scribble)

    suspend fun deleteScribble()
}