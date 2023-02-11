package com.manujain.notesy.core.presentation

import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

class WindowFullScreenActivatorImpl : WindowFullScreenActivator, LifecycleEventObserver {

    private var window: Window? = null

    override fun registerWindowWithLifecycle(owner: LifecycleOwner, window: Window) {
        this.window = window
        owner.lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                Timber.d("Lifecycle Event ${event.name}. Window Layout set to FLAG_LAYOUT_NO_LIMIT")
                window?.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }

            else -> {
                Timber.d("Lifecycle Event ${event.name}")
            }
        }
    }
}
