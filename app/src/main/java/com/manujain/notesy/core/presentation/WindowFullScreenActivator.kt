package com.manujain.notesy.core.presentation

import android.view.Window
import androidx.lifecycle.LifecycleOwner

interface WindowFullScreenActivator {
    fun registerWindowWithLifecycle(owner: LifecycleOwner, window: Window)
}
