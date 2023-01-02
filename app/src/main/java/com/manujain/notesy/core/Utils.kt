package com.manujain.notesy.core

import android.content.Context
import android.content.res.Configuration

object Utils {
    fun isNightMode(context: Context): Boolean {
        val currentUiMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return when (currentUiMode) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }
}
