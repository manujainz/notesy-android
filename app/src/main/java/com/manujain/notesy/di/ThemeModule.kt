package com.manujain.notesy.di

import com.manujain.notesy.MainApplication
import com.manujain.notesy.core.theme.repository.NotesyBackgroundProvider
import com.manujain.notesy.core.theme.repository.NotesyBackgroundProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ThemeModule {

    @Provides
    @Singleton
    fun provideNotesyBackgroundProvider(context: MainApplication): NotesyBackgroundProvider {
        return NotesyBackgroundProviderImpl(context)
    }
}
