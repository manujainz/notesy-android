package com.manujain.notesy.feature_scribblepad.data.repository

import com.manujain.notesy.feature_scribblepad.data.data_source.ScribbleDataStore
import com.manujain.notesy.feature_scribblepad.domain.model.Scribble
import com.manujain.notesy.feature_scribblepad.domain.repository.ScribblePadRepository

class ScribblePadRepositoryImpl(private val dataStore: ScribbleDataStore) : ScribblePadRepository {

    override suspend fun getScribble(): Scribble {
        return dataStore.getScribble()
    }

    override suspend fun storeScribble(scribble: Scribble) {
        dataStore.storeScribble(scribble)
    }

    override suspend fun deleteScribble() {
        dataStore.deleteScribble()
    }
}
