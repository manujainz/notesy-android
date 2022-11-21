package com.manujain.flashnotes.di

import com.manujain.flashnotes.MainApplication
import com.manujain.flashnotes.feature_scribblepad.data.data_source.ScribbleDataStore
import com.manujain.flashnotes.feature_scribblepad.data.data_source.SecurePrefScribbleDataStore
import com.manujain.flashnotes.feature_scribblepad.data.repository.ScribblePadRepositoryImpl
import com.manujain.flashnotes.feature_scribblepad.domain.repository.ScribblePadRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ScribblePadModule {

    @Provides
    @Singleton
    fun provideScribbleDataStore(context: MainApplication): ScribbleDataStore {
        return SecurePrefScribbleDataStore(context)
    }

    @Provides
    @Singleton
    fun provideScribblePadRepository(dataStore: ScribbleDataStore): ScribblePadRepository {
        return ScribblePadRepositoryImpl(dataStore)
    }
}
