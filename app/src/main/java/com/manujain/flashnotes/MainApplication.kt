package com.manujain.flashnotes

import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber

@HiltViewModel
class MainApplication: Application() {

    companion object {
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}